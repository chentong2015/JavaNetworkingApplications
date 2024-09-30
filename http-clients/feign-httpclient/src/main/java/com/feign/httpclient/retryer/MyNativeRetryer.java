package com.feign.httpclient.retryer;

import feign.RetryableException;
import feign.Retryer;

// TODO. Feign will automatically retry IOExceptions and any RetryableException thrown from an ErrorDecoder
//  默认只会在IO异常上重连或者在解码器上自定义抛出指定的异常
public class MyNativeRetryer implements feign.Retryer {

    // Retryers are responsible for determining if a retry should occur
    // A Retryer instance will be created for each Client execution,
    //  allowing you to maintain state bewteen each request if desired.
    @Override
    public void continueOrPropagate(RetryableException e) {
        // TODO. Retryer that will always retry calls after waiting one second:
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw e;
        }
    }

    @Override
    public Retryer clone() {
        return new MyNativeRetryer();
    }
}
