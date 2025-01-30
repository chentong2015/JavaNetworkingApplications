package cookies;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

// TODO. 自定义实现服务端的CookieStore，用于存储HttpCookie
// - cookie具有特定的过期时间hasExpired()
// - cookie在添加前需要先删除原来存在的
public class MyInMemoryCookieStore implements CookieStore {

    private List<HttpCookie> cookieList;
    private CookieStoreHelper cookieStoreHelper;

    // The cookies are indexed by its domain and associated uri (if present)
    // When a cookie removed from main data structure (i.e. cookieJar),
    // it won't be cleared in domainIndex & uriIndex.
    // Double-check the presence of cookie when retrieve one form index store.
    private Map<String, List<HttpCookie>> domainIndex;
    private Map<URI, List<HttpCookie>> uriIndex;

    // use ReentrantLock instead of synchronized for scalability
    private final ReentrantLock lock = new ReentrantLock(false);

    public MyInMemoryCookieStore() {
        cookieList = new ArrayList<>();
        domainIndex = new HashMap<>();
        uriIndex = new HashMap<>();
        cookieStoreHelper = new CookieStoreHelper(cookieList);
    }

    @Override
    public void add(URI uri, HttpCookie cookie) {
        if (cookie == null) {
            throw new NullPointerException("cookie is null");
        }
        lock.lock();
        try {
            cookieList.remove(cookie);
            if (cookie.getMaxAge() != 0) {
                cookieList.add(cookie);
                if (cookie.getDomain() != null) {
                    addIndex(domainIndex, cookie.getDomain(), cookie);
                }
                if (uri != null) {
                    // add it to uri index, too
                    addIndex(uriIndex, getEffectiveURI(uri), cookie);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Get all cookies, which:
     * 1. given uri domain-matches with, or associated with given uri when added to the cookie store.
     * 2. not expired.
     * See RFC 2965 sec. 3.3.4 for more detail.
     */
    @Override
    public List<HttpCookie> get(URI uri) {
        if (uri == null) {
            throw new NullPointerException("uri can't be null");
        }
        List<HttpCookie> cookies = new ArrayList<>();
        boolean secureLink = "https".equalsIgnoreCase(uri.getScheme());
        lock.lock();
        try {
            cookieStoreHelper.getInternal1(cookies, domainIndex, uri.getHost(), secureLink);
            cookieStoreHelper.getInternal2(cookies, uriIndex, getEffectiveURI(uri), secureLink);
        } finally {
            lock.unlock();
        }
        return cookies;
    }

    @Override
    public List<HttpCookie> getCookies() {
        List<HttpCookie> rt;
        lock.lock();
        try {
            cookieList.removeIf(HttpCookie::hasExpired);
        } finally {
            rt = Collections.unmodifiableList(cookieList);
            lock.unlock();
        }
        return rt;
    }

    @Override
    public List<URI> getURIs() {
        List<URI> uris;
        lock.lock();
        try {
            Iterator<URI> it = uriIndex.keySet().iterator();
            while (it.hasNext()) {
                URI uri = it.next();
                List<HttpCookie> cookies = uriIndex.get(uri);
                if (cookies == null || cookies.size() == 0) {
                    // no cookies list or an empty list associated with
                    // this uri entry, delete it
                    it.remove();
                }
            }
        } finally {
            uris = new ArrayList<>(uriIndex.keySet());
            lock.unlock();
        }
        return uris;
    }

    @Override
    public boolean remove(URI uri, HttpCookie ck) {
        // argument can't be null
        if (ck == null) {
            throw new NullPointerException("cookie is null");
        }
        boolean modified = false;
        lock.lock();
        try {
            modified = cookieList.remove(ck);
        } finally {
            lock.unlock();
        }

        return modified;
    }

    @Override
    public boolean removeAll() {
        lock.lock();
        try {
            if (cookieList.isEmpty()) {
                return false;
            }
            cookieList.clear();
            domainIndex.clear();
            uriIndex.clear();
        } finally {
            lock.unlock();
        }
        return true;
    }

    // add 'cookie' indexed by 'index' into 'indexStore'
    private <T> void addIndex(Map<T, List<HttpCookie>> indexStore, T index, HttpCookie cookie) {
        if (index != null) {
            List<HttpCookie> cookies = indexStore.get(index);
            if (cookies != null) {
                // there may already have the same cookie, so remove it first
                cookies.remove(cookie);
                cookies.add(cookie);
            } else {
                cookies = new ArrayList<>();
                cookies.add(cookie);
                indexStore.put(index, cookies);
            }
        }
    }

    // for cookie purpose, the effective uri should only be http://host
    // the path will be taken into account when path-match algorithm applied
    private URI getEffectiveURI(URI uri) {
        URI effectiveURI = null;
        try {
            effectiveURI = new URI("http", uri.getHost(), null, null, null);
        } catch (URISyntaxException ignored) {
            effectiveURI = uri;
        }
        return effectiveURI;
    }
}
