package com.feign.httpclient.exception;

import feign.Feign;
import feign.RetryableException;
import feign.httpclient.ApacheHttpClient;
import org.apache.http.conn.HttpHostConnectException;

// 指定的Url Endpoint没有开启或者无法访问时，在自动Retry之后抛出异常
// 在具体执行的时候，没有使用代理执行Feign client request
//
// Exception in thread "main" feign.RetryableException
//   at feign.FeignException.errorExecuting(FeignException.java:268)
//   ...
// Caused by: org.apache.http.conn.HttpHostConnectException: Connect to localhost/127.0.0.1 failed: Connection refused
//   at org.apache.http.impl.conn.DefaultHttpClientConnectionOperator.connect(DefaultHttpClientConnectionOperator.java:156)
//	 at org.apache.http.impl.conn.PoolingHttpClientConnectionManager.connect(PoolingHttpClientConnectionManager.java:376)
//   ...
// Caused by: java.net.ConnectException: Connection refused: no further information
//	 at java.base/sun.nio.ch.Net.pollConnect(Native Method)
//	 at java.base/sun.nio.ch.Net.pollConnectNow(Net.java:660)
//   ...
public class OpenFeignClientException {

    public static void main(String[] args) {
        try {
            testFeignClientException();
        } catch (RetryableException retryableException) {
            if (retryableException.getCause() instanceof HttpHostConnectException) {
                System.out.println("Connection refused");
            }
            System.out.println("Connection failed");
        }
    }

    private static void testFeignClientException() {
        String baseUrl = "https://localhost/";
        WithExceptionFeignClient feignClient = Feign.builder().client(new ApacheHttpClient())
                .target(WithExceptionFeignClient.class, baseUrl);
        feignClient.getItems();
    }
}
