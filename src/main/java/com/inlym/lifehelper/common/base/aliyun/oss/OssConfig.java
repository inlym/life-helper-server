package com.inlym.lifehelper.common.base.aliyun.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云 OSS 配置类（主要用于输出封装好的 OSS 客户端）
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-12
 * @since 1.0.0
 */
@Configuration
@RequiredArgsConstructor
public class OssConfig {
    private final OssProperties ossProperties;

    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
    }
}
