package com.inlym.lifehelper.external.tablestore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 表格存储实例
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/4/8
 * @since 1.1.0
 **/
@Configuration
public class TableStoreConfig {
    @Bean
    public TableStoreClient otsClient(TableStoreProperties properties) {
        String endpoint = properties.getEndpoint();
        String accessKeyId = properties.getAccessKeyId();
        String accessKeySecret = properties.getAccessKeySecret();
        String instanceName = properties.getInstanceName();
        return new TableStoreClient(endpoint, accessKeyId, accessKeySecret, instanceName);
    }
}
