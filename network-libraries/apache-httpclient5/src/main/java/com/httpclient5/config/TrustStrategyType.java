package com.httpclient5.config;

import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.ssl.TrustStrategy;

public enum TrustStrategyType {

    TRUST_SELF_SIGNED(new TrustSelfSignedStrategy()),
    TRUST_ALL(new TrustAllStrategy()),
    NONE(null);

    private final TrustStrategy strategy;

    TrustStrategyType(TrustStrategy strategy) {
        this.strategy = strategy;
    }

    public TrustStrategy getStrategy() {
        return this.strategy;
    }
}
