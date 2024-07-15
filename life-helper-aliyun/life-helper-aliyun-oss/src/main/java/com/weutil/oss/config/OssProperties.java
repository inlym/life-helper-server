package com.weutil.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 类名称
 *
 * <h2>说明
 * <p>说明文本内容
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/15
 * @since 3.0.0
 **/
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
public class OssProperties {
    /**
     * 存储空间名称
     *
     * <h4>示例
     * <p>{@code weutil-central}
     */
    private String bucketName;

    /**
     * 绑定的自定义域名
     *
     * <h4>示例
     * <p>{@code res.weutil.com}
     */
    private String customDomain;

    /**
     * 访问端口
     *
     * <h4>示例
     * <p>外网访问 {@code oss-cn-hangzhou.aliyuncs.com}
     * <p>VPC 网络访问 {@code oss-cn-hangzhou-internal.aliyuncs.com}
     */
    private String endpoint;

    /** 访问密钥 ID */
    private String accessKeyId;

    /** 访问密钥口令 */
    private String accessKeySecret;
}
