package com.httpclient5.interceptor;

import org.apache.hc.core5.http.EntityDetails;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpRequestInterceptor;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.http.HttpHeaders;

import java.io.IOException;

public class RemoveSoapHeadersInterceptor implements HttpRequestInterceptor {

    // TODO. 如果请求的头部中设置了如下两种值, Apache HttpClient将抛出异常
    @Override
    public void process(HttpRequest request, EntityDetails entityDetails, HttpContext httpContext)
            throws HttpException, IOException {
        if (request.containsHeader(HttpHeaders.TRANSFER_ENCODING)) {
            request.removeHeaders(HttpHeaders.TRANSFER_ENCODING);
        }
        if (request.containsHeader(HttpHeaders.CONTENT_LENGTH)) {
            request.removeHeaders(HttpHeaders.CONTENT_LENGTH);
        }
    }
}