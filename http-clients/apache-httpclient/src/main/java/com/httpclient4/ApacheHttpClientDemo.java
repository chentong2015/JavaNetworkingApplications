package com.httpclient4;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

// TODO. Apache Http Client异常
// 1. HttpClient在请求时自动开启slf4j日志输出，可以通过logback.xml自定义
// 2. HttpClient对于中文内容的请求存在不足，无法解析json数据 ?
public class ApacheHttpClientDemo {

    // 使用Apache HttpClientBuilder来构建自定义的Http Client
    public void testHttpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder
                .create()
                .setMaxConnPerRoute(50)
                .setMaxConnTotal(100);
        httpClientBuilder.setDefaultCookieStore(new BasicCookieStore());
        httpClientBuilder.disableAuthCaching();
    }

    public void testGetRequest() throws IOException {
        HttpGet request = new HttpGet("http://example.org");
        request.addHeader("User-Agent", "Chrome");

        CloseableHttpResponse response = HttpClients.createDefault().execute(request);
        response.getStatusLine().getStatusCode();

        // .getContent() 拿到所有返回的JSON数据
        InputStream inputStream = response.getEntity().getContent();
        String result = String.valueOf(inputStream);

        // 追行读取网络返回的Stream流
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
    }

    // 请求的HttpClient网络流需要关闭，推荐使用try-with-resources
    public void testPostRequest() throws IOException {
        HttpPost httpPost = new HttpPost("http://example.org");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        // Provide a json string as request body to the setEntity() method
        httpPost.setEntity(new StringEntity("json string"));
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(httpPost)) {

            // HttpEntity object contains the actual response. 使用工具类获取返回的body
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        }
    }
}
