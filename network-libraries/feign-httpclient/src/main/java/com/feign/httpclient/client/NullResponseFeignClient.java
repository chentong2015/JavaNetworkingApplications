package com.feign.httpclient.client;

import feign.Headers;
import feign.RequestLine;

import java.util.List;

@Headers("Accept: application/json")
public interface NullResponseFeignClient {

    // 测试Feign Client返回的Response可能为null
    @RequestLine("GET /v1/info/get")
    List<String> getInformation();
}


