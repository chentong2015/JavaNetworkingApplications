package com.feign.httpclient.exception;

import feign.Headers;
import feign.RequestLine;

import java.util.List;

@Headers("Accept: application/json")
public interface WithExceptionFeignClient {

    @RequestLine("GET /v1/test/items")
    List<String> getItems();

}
