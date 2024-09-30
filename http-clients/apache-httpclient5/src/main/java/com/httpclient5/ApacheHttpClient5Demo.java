package com.httpclient5;

import com.httpclient5.interceptor.MyResponseInterceptor;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpResponse;

import java.io.IOException;

public class ApacheHttpClient5Demo {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpclient = HttpClients.custom()
                .addResponseInterceptorFirst(new MyResponseInterceptor())
                .build();
        HttpGet httpget1 = new HttpGet("https://www.xxx.com/");
        HttpResponse httpresponse = httpclient.execute(httpget1);

        Header[] headers = httpresponse.getHeaders();
        for (Header header : headers) {
            System.out.println(header.getName());
        }
        httpclient.close();
    }
}
