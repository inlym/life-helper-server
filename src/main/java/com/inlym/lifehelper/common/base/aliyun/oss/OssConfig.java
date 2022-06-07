package com.inlym.lifehelper.common.base.aliyun.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OSS 配置类（主要用于输出封装好的 OSS 客户端）
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-12
 * @since 1.0.0
 */
@Configuration
public class OssConfig {
    @Bean
    public OSS ossClient(OssProperties ossProperties) {
        return new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
    }
}
