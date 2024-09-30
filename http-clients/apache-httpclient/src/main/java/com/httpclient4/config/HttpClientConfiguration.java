package com.httpclient4.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import com.httpclient4.interceptor.RemoveSoapHeadersInterceptor;

public abstract class HttpClientConfiguration {

    // TODO. RequestFactory中的ReadTimeout会被设置到RequestConfig的SocketTimeout
    //  以上两种Timeout属于不同的语义概念
    protected final ClientHttpRequestFactory createRequestFactory() throws Exception {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient());
        requestFactory.setReadTimeout(100);
        requestFactory.setConnectTimeout(100);
        return requestFactory;
    }

    // TODO. 通过HttpClientBuilder来构建HttpClient, 并配置请求参数
    protected final HttpClient httpClient() throws Exception {
        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(this.sslContext());

        HttpClientBuilder builder = HttpClientBuilder.create()
                .setSSLSocketFactory(factory)
                .addInterceptorFirst(new RemoveSoapHeadersInterceptor());
        return builder.build();
    }

    protected SSLContext sslContext() throws Exception {
        SSLContextBuilder builder = SSLContextBuilder.create();
        // To do ..
        return builder.build();
    }
}
