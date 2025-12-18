package com.feign.httpclient.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Optional;

// 在拦截请求的过程中，如果设置有私有缓存，则设置成共有缓存
public class CacheRequestInterceptor implements RequestInterceptor {

    private static final String CACHE_CONTROL = "Cache-Control";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (requestTemplate.headers().containsKey(CACHE_CONTROL)) {
            Optional<String> cache = requestTemplate.headers().get(CACHE_CONTROL).stream().findFirst();
            if (cache.isPresent() && cache.get().equals("private")) {
                requestTemplate.header(CACHE_CONTROL, "public");
            }
        }
    }
}
