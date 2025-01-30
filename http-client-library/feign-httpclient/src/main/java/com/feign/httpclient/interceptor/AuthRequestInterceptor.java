package com.feign.httpclient.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.http.HttpHeaders;

public class AuthRequestInterceptor implements RequestInterceptor {

    // 可以在请求的拦截器中添加对请求的token认证
    // 设置request headers中指定的name对应的value
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = getAuthorizationToken();
        if (!token.isEmpty()) {
            if (!requestTemplate.headers().containsKey(HttpHeaders.AUTHORIZATION)) {
                requestTemplate.header(HttpHeaders.AUTHORIZATION);
                requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            }
        }
    }

    private String getAuthorizationToken() {
        return "my token";
    }
}
