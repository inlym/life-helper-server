package com.inlym.lifehelper.external.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * OSS 配置信息
 *
 * @author inlym
 * @date 2022-02-12 23:10
 */
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
public class OssProperties {
    /** 访问密钥 ID */
    private String accessKeyId;

    /** 访问密钥口令 */
    private String accessKeySecret;

    /** 访问域名 */
    private String endpoint;

    /** 存储空间名称 */
    private String bucketName;

    /** 自定义 URL 地址 */
    private String aliasUrl;
}
