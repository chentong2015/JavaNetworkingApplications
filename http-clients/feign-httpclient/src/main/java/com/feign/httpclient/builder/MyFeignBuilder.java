package com.feign.httpclient.builder;

import com.feign.httpclient.target.MyFeignTarget;
import feign.Client;
import feign.Feign;
import feign.Target;

import java.net.URL;
import java.util.Objects;
import java.util.function.Supplier;

// TODO. 对Feign Builder的继承和自定义的实现
public class MyFeignBuilder extends Feign.Builder {

    private Supplier<Client> clientSupplier;
    private final URL defaultLoadBalancerUrl;

    public MyFeignBuilder(Supplier<Client> clientSupplier, URL defaultLoadBalancerUrl) {
        this.clientSupplier = clientSupplier;
        this.defaultLoadBalancerUrl = defaultLoadBalancerUrl;
    }

    @Override
    public Feign.Builder client(Client client) {
        Objects.requireNonNull(client, "Feign client cannot be null");
        this.clientSupplier = () -> client;
        return this;
    }

    // 创建自定义实现的Feign
    @Override
    public Feign build() {
        super.client(clientSupplier.get());
        final Feign feign = super.build();
        return new Feign() {
            @Override
            public <T> T newInstance(final Target<T> target) {
                return feign.newInstance(new MyFeignTarget<>(target, defaultLoadBalancerUrl));
            }
        };
    }
}
