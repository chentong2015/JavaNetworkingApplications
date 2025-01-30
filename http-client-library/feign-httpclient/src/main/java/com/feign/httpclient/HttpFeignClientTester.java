package com.feign.httpclient;

import com.feign.httpclient.client.FeignRequestClient;
import com.feign.httpclient.client.NullResponseFeignClient;
import com.feign.httpclient.interceptor.AuthRequestInterceptor;
import com.feign.httpclient.interceptor.CacheRequestInterceptor;
import com.feign.httpclient.retryer.MyNativeRetryer;
import feign.*;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HttpFeignClientTester {

    public static void main(String[] args) {
        Target<NullResponseFeignClient> target = new Target
                .HardCodedTarget<>(NullResponseFeignClient.class, "http://localhost:8080/");
        NullResponseFeignClient client = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()) // 必须添加解码器来解析返回的json数据
                .target(target);
        List<String> result = client.getInformation();
        if (result == null) {
            System.out.println("response null"); // 请求的结果有可能返回为null
        } else {
            System.out.println("result ok");
        }
    }

    // TODO. 实战项目中构建Target时可能需要获取Load Balancer并动态地发送到Endpoints
    public void testFeignBuilder() {
        FeignRequestClient feignClient = Feign.builder()
                .client(new ApacheHttpClient())  // 设置选择的Http client
                .encoder(new JacksonEncoder())   // 编码和解码器的选择
                .decoder(new JacksonDecoder())
                .retryer(new MyNativeRetryer())  // 添加retryer重连器
                .requestInterceptor(new AuthRequestInterceptor()) // 添加拦截器设置
                .requestInterceptor(new CacheRequestInterceptor())// 添加多个拦截器
                .target(FeignRequestClient.class, "https://localhost/demo"); // 设置target目标的默认url网络路径
        feignClient.callChaosFast();

        // 使用Target构建，但本质上还是通过Feign.builder()来target
        Target<FeignRequestClient> clientTarget = new Target
                .HardCodedTarget<>(FeignRequestClient.class, "https://localhost/");
        FeignRequestClient client = Feign.builder().target(clientTarget);
        client.callChaosFast();
    }

    // 使用Option来配置Feign请求的timeout参数
    public static void testFeignClientTimeout() {
        Feign.Builder builder = Feign.builder();
        builder.options(new Request.Options(100, TimeUnit.SECONDS, 100, TimeUnit.SECONDS,
                true));
    }

    // 使用默认的feign client retryer: 重试间隔时间，重试最大周期，最大尝试次数
    public static void testFeignDefaultRetryer() {
        Feign.Builder builder = Feign.builder();
        Retryer retryer = new Retryer.Default(100L, TimeUnit.SECONDS.toSeconds(3L), 5);
        builder.retryer(retryer);
    }

    // TODO. 构建异步的Feign Client
    public void testAsyncFeignClient() {
        FeignRequestClient feignRequestClient = AsyncFeign.asyncBuilder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .requestInterceptor(new AuthRequestInterceptor())
                .target(FeignRequestClient.class, "http://localhost:8082");
        feignRequestClient.callChaosFast();
    }
}
