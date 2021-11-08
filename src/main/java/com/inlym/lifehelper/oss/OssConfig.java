package com.inlym.lifehelper.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oss")
@Data
public class OssConfig {
    private String accessKeyId;

    private String accessKeySecret;

    /**
     * 访问域名
     */
    private String endpoint;
}
