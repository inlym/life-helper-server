package com.inlym.lifehelper.common.base.aliyun.ots;

import com.inlym.lifehelper.common.base.aliyun.ots.widecolumn.WideColumnClient;
import com.inlym.lifehelper.common.base.aliyun.ots.widecolumn.WideColumnProperties;
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

    @Bean
    public WideColumnClient wideColumnClient() {
        String endpoint = wideColumnProperties.getEndpoint();
        String accessKeyId = wideColumnProperties.getAccessKeyId();
        String accessKeySecret = wideColumnProperties.getAccessKeySecret();
        String instanceName = wideColumnProperties.getInstanceName();
        return new WideColumnClient(endpoint, accessKeyId, accessKeySecret, instanceName);
    }
}
