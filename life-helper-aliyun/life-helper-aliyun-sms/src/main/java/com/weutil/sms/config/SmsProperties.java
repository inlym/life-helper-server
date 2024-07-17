package com.weutil.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云短信服务配置信息
 *
 * <h2>主要用途
 * <p>管理阿里云短信服务的相关密钥信息。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/16
 * @since 3.0.0
 **/
@Component
@ConfigurationProperties(prefix = "aliyun.sms")
@Data
public class SmsProperties {
    /** 访问密钥 ID */
    private String accessKeyId;

    /** 访问密钥口令 */
    private String accessKeySecret;

    /** 短信签名名称 */
    private String signName;
}
