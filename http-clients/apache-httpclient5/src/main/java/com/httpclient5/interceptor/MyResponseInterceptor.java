package com.httpclient5.interceptor;

import org.apache.hc.core5.http.EntityDetails;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpResponseInterceptor;
import org.apache.hc.core5.http.protocol.HttpContext;

import java.io.IOException;

public class MyResponseInterceptor implements HttpResponseInterceptor {

    // 拦截返回的Response并做额外的设置
    @Override
    public void process(HttpResponse httpResponse, EntityDetails entityDetails, HttpContext httpContext)
            throws HttpException, IOException {
        System.out.println("Adding header sample_header, demo-header, test_header to the response");
        httpResponse.setHeader("sample-header", "My first header");
        httpResponse.setHeader("demo-header", "My second header");
        httpResponse.setHeader("test-header", "My third header");
    }
}
