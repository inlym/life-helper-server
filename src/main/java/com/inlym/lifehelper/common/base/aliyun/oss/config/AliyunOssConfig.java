package com.inlym.lifehelper.common.base.aliyun.oss.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云 OSS 配置
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/24
 * @since 2.2.0
 **/
@Configuration
@RequiredArgsConstructor
public class AliyunOssConfig {
    private final AliyunOssProperties properties;

    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder().build(properties.getEndpoint(), properties.getAccessKeyId(), properties.getAccessKeySecret());
    }
}
