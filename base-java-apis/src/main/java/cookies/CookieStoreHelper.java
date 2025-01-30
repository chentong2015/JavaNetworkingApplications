package cookies;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CookieStoreHelper {

    private final List<HttpCookie> cookieList;

    public CookieStoreHelper(List<HttpCookie> cookieList) {
        this.cookieList = cookieList;
    }

    /*
     * This is almost the same as HttpCookie.domainMatches except for
     * one difference: It won't reject cookies when the 'H' part of the
     * domain contains a dot ('.').
     * I.E.: RFC 2965 section 3.3.2 says that if host is x.y.domain.com
     * and the cookie domain is .domain.com, then it should be rejected.
     * However that's not how the real world works. Browsers don't reject and
     * some sites, like yahoo.com do actually expect these cookies to be
     * passed along.
     * And should be used for 'old' style cookies (aka Netscape type of cookies)
     */
    public boolean netscapeDomainMatches(String domain, String host) {
        if (domain == null || host == null) {
            return false;
        }
        // if there's no embedded dot in domain and domain is not .local
        boolean isLocalDomain = ".local".equalsIgnoreCase(domain);
        int embeddedDotInDomain = domain.indexOf('.');
        if (embeddedDotInDomain == 0) {
            embeddedDotInDomain = domain.indexOf('.', 1);
        }
        if (!isLocalDomain && (embeddedDotInDomain == -1 || embeddedDotInDomain == domain.length() - 1)) {
            return false;
        }
        // if the host name contains no dot and the domain name is .local
        int firstDotInHost = host.indexOf('.');
        if (firstDotInHost == -1 && isLocalDomain) {
            return true;
        }

        int domainLength = domain.length();
        int lengthDiff = host.length() - domainLength;
        if (lengthDiff == 0) {
            // if the host name and the domain name are just string-compare equal
            return host.equalsIgnoreCase(domain);
        } else if (lengthDiff > 0) {
            // need to check H & D component
            String H = host.substring(0, lengthDiff);
            String D = host.substring(lengthDiff);
            return (D.equalsIgnoreCase(domain));
        } else if (lengthDiff == -1) {
            // if domain is actually .host
            return (domain.charAt(0) == '.' &&
                    host.equalsIgnoreCase(domain.substring(1)));
        }
        return false;
    }

    public void getInternal1(List<HttpCookie> cookies, Map<String, List<HttpCookie>> cookieIndex,
                              String host, boolean secureLink) {
        // Use a separate list to handle cookies that need to be removed so
        // that there is no conflict with iterators.
        ArrayList<HttpCookie> toRemove = new ArrayList<>();
        for (Map.Entry<String, List<HttpCookie>> entry : cookieIndex.entrySet()) {
            String domain = entry.getKey();
            List<HttpCookie> lst = entry.getValue();
            for (HttpCookie c : lst) {
                if ((c.getVersion() == 0 && netscapeDomainMatches(domain, host)) ||
                        (c.getVersion() == 1 && HttpCookie.domainMatches(domain, host))) {
                    if ((cookieList.contains(c))) {
                        // the cookie still in main cookie store
                        if (!c.hasExpired()) {
                            // don't add twice and make sure it's the proper
                            // security level
                            if ((secureLink || !c.getSecure()) && !cookies.contains(c)) {
                                cookies.add(c);
                            }
                        } else {
                            toRemove.add(c);
                        }
                    } else {
                        // the cookie has been removed from main store,
                        // so also remove it from domain indexed store
                        toRemove.add(c);
                    }
                }
            }
            // Clear up the cookies that need to be removed
            for (HttpCookie c : toRemove) {
                lst.remove(c);
                cookieList.remove(c);
            }
            toRemove.clear();
        }
    }

    // @param cookies           [OUT] contains the found cookies
    // @param cookieIndex       the index
    // @param comparator        the prediction to decide whether or not
    //                          a cookie in index should be returned
    public <T> void getInternal2(List<HttpCookie> cookies, Map<T, List<HttpCookie>> cookieIndex,
                                  Comparable<T> comparator, boolean secureLink) {
        for (T index : cookieIndex.keySet()) {
            if (comparator.compareTo(index) == 0) {
                List<HttpCookie> indexedCookies = cookieIndex.get(index);
                // check the list of cookies associated with this domain
                if (indexedCookies != null) {
                    Iterator<HttpCookie> it = indexedCookies.iterator();
                    while (it.hasNext()) {
                        HttpCookie ck = it.next();
                        if (cookieList.contains(ck)) {
                            // the cookie still in main cookie store
                            if (!ck.hasExpired()) {
                                // don't add twice
                                if ((secureLink || !ck.getSecure()) && !cookies.contains(ck))
                                    cookies.add(ck);
                            } else {
                                it.remove();
                                cookieList.remove(ck);
                            }
                        } else {
                            // the cookie has been removed from main store,
                            // so also remove it from domain indexed store
                            it.remove();
                        }
                    }
                }
            }
        }
    }

}
