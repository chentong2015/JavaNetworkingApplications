package com.httpclient5.config;

import org.springframework.core.io.Resource;

public class SslProperties {

    public static final String PROTOCOL_PROPERTY = "protocol";
    public static final String TRUST_STRATEGY_PROPERTY = "trustStrategy";
    public static final String TRUST_STORE_PROPERTY = "trustStore";
    public static final String TRUST_STORE_PASSWORD_PROPERTY = "trustStorePassword";
    public static final String KEY_STORE_PROPERTY = "keyStore";
    public static final String KEY_STORE_PASSWORD_PROPERTY = "keyStorePassword";
    public static final String KEY_PASSWORD_PROPERTY = "keyPassword";
    public static final String KEY_ALIAS_PROPERTY = "keyAlias";
    public static final String TIMEOUT_PROPERTY = "timeout";

    private final String webServiceType;
    private ProtocolType protocol;
    private TrustStrategyType trustStrategy = TrustStrategyType.NONE;
    private Resource trustStore;
    private String trustStorePassword;
    private Resource keyStore;
    private String keyStorePassword;
    private String keyAlias;
    private String keyPassword;
    private Integer timeout;

    public SslProperties(String webServiceType) {
        this.webServiceType = webServiceType;
    }

    public String getWebServiceType() {
        return this.webServiceType;
    }

    public ProtocolType getProtocol() {
        return this.protocol;
    }

    public void setProtocol(ProtocolType protocol) {
        this.protocol = protocol;
    }

    public TrustStrategyType getTrustStrategy() {
        return this.trustStrategy;
    }

    public void setTrustStrategy(TrustStrategyType trustStrategy) {
        this.trustStrategy = trustStrategy;
    }

    public Resource getTrustStore() {
        return this.trustStore;
    }

    public void setTrustStore(Resource trustStore) {
        this.trustStore = trustStore;
    }

    public String getTrustStorePassword() {
        return this.trustStorePassword;
    }

    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    public Resource getKeyStore() {
        return this.keyStore;
    }

    public void setKeyStore(Resource keyStore) {
        this.keyStore = keyStore;
    }

    public String getKeyStorePassword() {
        return this.keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public String getKeyAlias() {
        return this.keyAlias;
    }

    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    public String getKeyPassword() {
        return this.keyPassword;
    }

    public void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }

    public int getTimeoutMillis() {
        return this.timeout * 1000;
    }

    public Integer getTimeout() {
        return this.timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
