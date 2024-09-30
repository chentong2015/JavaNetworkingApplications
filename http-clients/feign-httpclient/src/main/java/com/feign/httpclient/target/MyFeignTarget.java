package com.feign.httpclient.target;

import feign.Request;
import feign.RequestTemplate;
import feign.Target;

import java.net.URL;

public class MyFeignTarget<T> implements Target<T> {

    private final Target<T> target;
    private final URL defaultLoadBalancerUrl;

    public MyFeignTarget(Target<T> target, URL defaultLoadBalancerUrl) {
        this.target = target;
        this.defaultLoadBalancerUrl = defaultLoadBalancerUrl;
    }

    @Override
    public Class<T> type() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public String url() {
        return null;
    }

    // TODO. 在此方法中对requestTemplate请求进行判断，创建相应的request 
    @Override
    public Request apply(RequestTemplate requestTemplate) {
        Request request;
        if (target instanceof EmptyTarget) {
            request = requestTemplate.request();
        } else {
            request = target.apply(requestTemplate);
        }
        if (requestTemplate.url().contains("http") && this.defaultLoadBalancerUrl != null) {
            String requestUrl = this.defaultLoadBalancerUrl + "/" + request.url();
            return Request.create(
                    request.httpMethod(),
                    requestUrl,
                    request.headers(),
                    Request.Body.create(request.body()),
                    requestTemplate);
        }
        return request;
    }
}
