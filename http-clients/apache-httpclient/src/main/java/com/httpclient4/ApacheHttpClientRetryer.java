package com.httpclient4;

import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.SocketException;

public class ApacheHttpClientRetryer implements HttpRequestRetryHandler {

    private static final int DEFAULT_MAX_RETRY_COUNT = 5;
    private static final long DEFAULT_INTERVAL = 50L;

    // TODO. 返回true表示会自动重试，在sleep指定的时间间隔之后; false不再进行重试
    // By default, Feign will automatically retry IOException !!
    // Retryers will determine if a retry should occur by returning either a true or false
    @Override
    public boolean retryRequest(IOException e, int countRetry, HttpContext httpContext) {
        if (countRetry > DEFAULT_MAX_RETRY_COUNT) {
            return false;
        }
        if (isRetryableException(e)) {
            try {
                Thread.sleep(DEFAULT_INTERVAL * countRetry);
                return true;
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }

    // TODO. Http Client 默认会重试的两种异常类型
    // 判断是否是指定的需要重试异常类型，或者可以从异常的message中判断是否需要重试
    static boolean isRetryableException(IOException e) {
        return e instanceof NoHttpResponseException || e instanceof SocketException;
    }
}
