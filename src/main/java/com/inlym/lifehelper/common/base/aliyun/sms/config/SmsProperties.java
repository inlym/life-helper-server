package com.inlym.lifehelper.common.base.aliyun.sms.config;

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
 * @date 2023/7/18
 * @since 2.0.2
 **/
@Component
@ConfigurationProperties(prefix = "aliyun.sms")
@Data
public class SmsProperties {
    /** 访问密钥 ID */
    private String accessKeyId;

    /** 访问密钥口令 */
    private String accessKeySecret;
}
