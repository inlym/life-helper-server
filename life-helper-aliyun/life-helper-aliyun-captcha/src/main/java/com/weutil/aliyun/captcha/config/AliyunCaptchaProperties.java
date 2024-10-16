package com.weutil.aliyun.captcha.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云验证码服务配置信息
 *
 * <h2>主要用途
 * <p>管理阿里云验证码服务的相关密钥信息。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/10/16
 * @since 3.0.0
 **/
@Component
@ConfigurationProperties(prefix = "aliyun.captcha")
@Data
public class AliyunCaptchaProperties {
    /** 访问密钥 ID */
    private String accessKeyId;

    /** 访问密钥口令 */
    private String accessKeySecret;

    /** 场景 ID */
    private String sceneId;
}
