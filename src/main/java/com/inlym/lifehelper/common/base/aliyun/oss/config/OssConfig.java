package com.inlym.lifehelper.common.base.aliyun.oss.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.Protocol;
import com.inlym.lifehelper.common.base.aliyun.oss.service.OssEndpointService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OSS 客户端配置
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/8
 * @since 2.3.0
 **/
@Configuration
@RequiredArgsConstructor
public class OssConfig {
    private final CentralBucketProperties centralBucketProperties;

    private final UserUploadBucketProperties userUploadBucketProperties;

    private final OssEndpointService ossEndpointService;

    @Bean
    public OSS centralBucketClient() {
        String endpoint = ossEndpointService.getEndpoint();
        String accessKeyId = centralBucketProperties.getAccessKeyId();
        String accessKeySecret = centralBucketProperties.getAccessKeySecret();

        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    @Bean
    public OSS centralBucketWithCustomDomainClient() {
        String endpoint = centralBucketProperties.getCustomDomain();
        String accessKeyId = centralBucketProperties.getAccessKeyId();
        String accessKeySecret = centralBucketProperties.getAccessKeySecret();

        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        conf.setProtocol(Protocol.HTTPS);
        conf.setSupportCname(true);

        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, conf);
    }

    @Bean
    public OSS userUploadBucketClient() {
        String endpoint = ossEndpointService.getEndpoint();
        String accessKeyId = userUploadBucketProperties.getAccessKeyId();
        String accessKeySecret = userUploadBucketProperties.getAccessKeySecret();

        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
