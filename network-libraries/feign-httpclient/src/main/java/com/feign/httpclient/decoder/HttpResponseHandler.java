package com.feign.httpclient.decoder;

import feign.Response;
import org.apache.http.HttpHeaders;

import java.util.Collection;
import java.util.Date;

public class HttpResponseHandler {

    public static boolean shouldRetryAfter(Response response) {
        return response.headers().containsKey(HttpHeaders.RETRY_AFTER)
                || response.status() >= 500;
    }

    public static Date parseRetryDateFromHeader(Response response) {
        Collection<String> values = response.headers().get(HttpHeaders.RETRY_AFTER);
        if (values == null) {
            return null;
        }
        // Convert string to date according to format pattern
        return new Date();
    }
}
