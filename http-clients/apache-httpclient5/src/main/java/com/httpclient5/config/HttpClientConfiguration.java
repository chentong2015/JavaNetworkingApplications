package com.httpclient5.config;

import com.httpclient5.interceptor.RemoveSoapHeadersInterceptor;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.ssl.SSLContextBuilder;

import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.SSLContext;
import java.io.File;

public abstract class HttpClientConfiguration {

    protected final ClientHttpRequestFactory createRequestFactory() throws Exception {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(this.httpClient());
        requestFactory.setConnectTimeout(100);
        return requestFactory;
    }

    protected final HttpClient httpClient() throws Exception {
        HttpClientBuilder builder = HttpClientBuilder.create()
                .setConnectionManager(getConnectionManager())
                .addRequestInterceptorFirst(new RemoveSoapHeadersInterceptor());;
        return builder.build();
    }

    // TODO. 通过Connection Manager来配置Http请求的设置
    private PoolingHttpClientConnectionManager getConnectionManager() {
        return PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
                        .setSslContext(SSLContexts.createSystemDefault())
                        .setTlsVersions(TLS.V_1_3)
                        .build())
                .setDefaultSocketConfig(SocketConfig.custom()
                        .setSoTimeout(Timeout.ofMinutes(1))
                        .build())
                .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                .setConnPoolPolicy(PoolReusePolicy.LIFO)
                .setDefaultConnectionConfig(ConnectionConfig.custom()
                        .setSocketTimeout(Timeout.ofMinutes(1))
                        .setConnectTimeout(Timeout.ofMinutes(1))
                        .setTimeToLive(TimeValue.ofMinutes(10))
                        .build())
                .build();
    }

    protected SSLConnectionSocketFactory connectionSocketFactory() throws Exception {
        return new SSLConnectionSocketFactory(this.sslContext());
    }

    protected SSLContext sslContext() throws Exception {
        SslProperties properties = this.getSslProperties();
        SSLContextBuilder builder = SSLContextBuilder.create();

        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();

        // Load TrustStore ?
        if (properties.getTrustStrategy() == TrustStrategyType.TRUST_ALL) {
            builder.loadTrustMaterial(new TrustAllStrategy());
            // builder.loadTrustMaterial
        } else {
            System.out.println("other case");
        }
        // Todo
        return builder.build();
    }

    protected abstract SslProperties getSslProperties();
}
