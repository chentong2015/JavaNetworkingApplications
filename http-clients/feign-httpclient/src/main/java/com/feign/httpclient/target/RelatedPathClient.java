package com.feign.httpclient.target;

import feign.RequestLine;

// 这个请求的client必须提供一个绝对的URL
public interface RelatedPathClient {

    @RequestLine("GET /v1/sample/index")
    String index();
}
