package com.weutil.aliyun.captcha.config;

import com.aliyun.teaopenapi.models.Config;
import com.weutil.aliyun.captcha.model.AliyunCaptchaClient;
import com.weutil.common.exception.ServerSideTemporaryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云验证码服务配置
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/10/16
 * @since 3.0.0
 **/
@Configuration
@Slf4j
@RequiredArgsConstructor
public class AliyunCaptchaConfig {
    private final AliyunCaptchaProperties properties;

    @Bean
    public AliyunCaptchaClient aliyunCaptchaClient() {
        Config config = new Config();
        config.setAccessKeyId(properties.getAccessKeyId());
        config.setAccessKeySecret(properties.getAccessKeySecret());
        config.setEndpoint("captcha.cn-shanghai.aliyuncs.com");

        try {
            return new AliyunCaptchaClient(config);
        } catch (Exception e) {
            log.error("阿里云验证码服务客户端创建错误，错误消息：{}", e.getMessage());
            throw new ServerSideTemporaryException();
        }
    }
}
