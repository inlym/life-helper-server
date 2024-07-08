package com.weutil.common.base.aliyun.ots.config;

import com.alicloud.openservices.tablestore.TimeseriesClient;
import com.weutil.common.base.aliyun.ots.core.model.WideColumnClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云表格存储配置
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/13
 * @since 1.3.0
 **/
@Configuration
@RequiredArgsConstructor
public class TableStoreConfig {
    private final WideColumnProperties wideColumnProperties;

    /**
     * 宽列模型客户端
     */
    @Bean
    public WideColumnClient wideColumnClient() {
        String endpoint = wideColumnProperties.getEndpoint();
        String accessKeyId = wideColumnProperties.getAccessKeyId();
        String accessKeySecret = wideColumnProperties.getAccessKeySecret();
        String instanceName = wideColumnProperties.getInstanceName();
        return new WideColumnClient(endpoint, accessKeyId, accessKeySecret, instanceName);
    }

    /**
     * 时序模型客户端
     *
     * @since 1.7.0
     */
    @Bean
    public TimeseriesClient timeseriesClient() {
        String endpoint = wideColumnProperties.getEndpoint();
        String accessKeyId = wideColumnProperties.getAccessKeyId();
        String accessKeySecret = wideColumnProperties.getAccessKeySecret();
        String instanceName = wideColumnProperties.getInstanceName();
        return new TimeseriesClient(endpoint, accessKeyId, accessKeySecret, instanceName);
    }
}
