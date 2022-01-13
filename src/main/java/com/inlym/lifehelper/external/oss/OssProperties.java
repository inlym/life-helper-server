package com.inlym.lifehelper.external.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
public class OssProperties {
    private String accessKeyId;

    private String accessKeySecret;

    /**
     * 访问域名
     */
    private String endpoint;
}
