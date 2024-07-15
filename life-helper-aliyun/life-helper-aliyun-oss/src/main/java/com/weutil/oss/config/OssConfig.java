package com.weutil.oss.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.Protocol;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OSS 客户端配置
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/15
 * @since 3.0.0
 **/
@Configuration
@RequiredArgsConstructor
public class OssConfig {
    private final OssProperties ossProperties;

    /**
     * 通用 OSS 客户端
     *
     * @date 2024/7/15
     * @since 3.0.0
     */
    @Bean
    public OSS ossClient() {
        String endpoint = ossProperties.getEndpoint();
        String accessKeyId = ossProperties.getAccessKeyId();
        String accessKeySecret = ossProperties.getAccessKeySecret();

        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 专用于“预签名”的 OSS 客户端
     *
     * @date 2024/7/15
     * @since 3.0.0
     */
    @Bean
    public OSS ossClientForPresigning() {
        String endpoint = ossProperties.getCustomDomain();
        String accessKeyId = ossProperties.getAccessKeyId();
        String accessKeySecret = ossProperties.getAccessKeySecret();

        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        conf.setProtocol(Protocol.HTTPS);
        conf.setSupportCname(true);

        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, conf);
    }
}
