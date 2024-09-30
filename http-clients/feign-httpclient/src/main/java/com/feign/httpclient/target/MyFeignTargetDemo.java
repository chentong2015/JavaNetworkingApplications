package com.feign.httpclient.target;

import feign.Feign;
import feign.Retryer;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.Encoder;

// Target: Feign Client请求访问的目标地址
// 包含两种类型的target: EmptyTarget / HardCodedTarget
public class MyFeignTargetDemo {

    public void testFeignClientTarget() {
        // 1. EmptyTarget does not contain any base url
        // => {baseUri}/v1/sample/index
        Target<AbsolutePathClient> target1 = Target.EmptyTarget.create(AbsolutePathClient.class);
        // => Exception: "Request with non-absolute URL not supported with empty target"
        Target<RelatedPathClient> target2 = Target.EmptyTarget.create(RelatedPathClient.class);
        RelatedPathClient apiClient = new Feign.Builder().target(target2);


        // 2. HardCodedTarget contains a hard coded base url
        // => {baseUri}/v1/sample/index, http://anotherhost:8080 is ignored
        Target<AbsolutePathClient> target3 = new Target.HardCodedTarget<>(
                AbsolutePathClient.class, "http://anotherhost:8080");
        // => Exception: "target values must be absolute"
        Target<RelatedPathClient> target4 = new Target.HardCodedTarget<>(
                RelatedPathClient.class, "related/path");
        // => http://myhost:8080/v1/sample/index
        Target<RelatedPathClient> target5 = new Target.HardCodedTarget<>(
                RelatedPathClient.class, "http://myhost:8080");
    }

    // TODO. 泛型方法，传入任何的feign client interface, 都可以构建出target
    public <T> T createMyFeignClient(String name, Class<T> clazz) {
        return Feign.builder()
                .encoder(new Encoder.Default())
                .decoder(new Decoder.Default())
                .retryer(Retryer.NEVER_RETRY)
                .target(Target.EmptyTarget.create(clazz));
    }
}
