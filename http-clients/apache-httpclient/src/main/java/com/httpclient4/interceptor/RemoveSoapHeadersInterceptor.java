package com.httpclient4.interceptor;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HTTP;

import java.io.IOException;

public class RemoveSoapHeadersInterceptor implements HttpRequestInterceptor {

    @Override
    public void process(HttpRequest request, HttpContext context) throws IOException {
        if (request instanceof HttpEntityEnclosingRequest) {
            if (request.containsHeader(HTTP.TRANSFER_ENCODING)) {
                request.removeHeaders(HTTP.TRANSFER_ENCODING);
            }
            if (request.containsHeader(HTTP.CONTENT_LEN)) {
                request.removeHeaders(HTTP.CONTENT_LEN);
            }
        }
    }
}
