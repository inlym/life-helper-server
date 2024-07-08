package com.weutil.common.base.aliyun.oss.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.Protocol;
import com.weutil.common.base.aliyun.oss.constant.OssEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

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

    private final Environment environment;

    @Bean
    public OSS centralBucketClient() {
        String endpoint = getEndpoint();
        String accessKeyId = centralBucketProperties.getAccessKeyId();
        String accessKeySecret = centralBucketProperties.getAccessKeySecret();

        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 获取 OSS 访问地址
     *
     * <h3>说明
     * <p>除了在生产环境（{@code prod}）使用内网地址访问外，其余情况均直接使用公网访问。
     */
    private String getEndpoint() {
        String envName = environment.getProperty("spring.profiles.active");
        if ("prod".equalsIgnoreCase(envName)) {
            return OssEndpoint.INTERNAL_NETWORK.getDomain();
        }

        return OssEndpoint.PUBLIC_NETWORK.getDomain();
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
        String endpoint = getEndpoint();
        String accessKeyId = userUploadBucketProperties.getAccessKeyId();
        String accessKeySecret = userUploadBucketProperties.getAccessKeySecret();

        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
