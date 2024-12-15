package com.weutil.sms.config;

import com.aliyun.teaopenapi.models.Config;
import com.weutil.common.exception.ServerSideTemporaryException;
import com.weutil.sms.model.AliyunSmsClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云短信客户端配置
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/16
 * @since 3.0.0
 **/
@Configuration
@Slf4j
@RequiredArgsConstructor
public class AliyunSmsConfig {
    private final AliyunSmsProperties properties;

    @Bean
    public AliyunSmsClient aliyunSmsClient() {
        Config config = new Config()
            .setAccessKeyId(properties.getAccessKeyId())
            .setAccessKeySecret(properties.getAccessKeySecret())
            .setEndpoint("dysmsapi.aliyuncs.com");

        try {
            return new AliyunSmsClient(config);
        } catch (Exception e) {
            log.error("阿里云短信客户端创建错误，错误消息：{}", e.getMessage());
            throw new ServerSideTemporaryException();
        }
    }
}
