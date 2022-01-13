package com.inlym.lifehelper.external.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
public class OSSProperties {
    private String accessKeyId;

    private String accessKeySecret;

    /**
     * 访问域名
     */
    private String endpoint;

    /**
     * 存储桶名称
     */
    private String bucketName;
}
