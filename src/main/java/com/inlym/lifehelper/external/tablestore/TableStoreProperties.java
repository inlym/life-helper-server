package com.inlym.lifehelper.external.tablestore;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云表格存储（TableStore）配置信息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/4/8
 * @since 1.1.0
 **/
@Component
@ConfigurationProperties(prefix = "aliyun.tablestore")
@Data
public class TableStoreProperties {
    /** 访问密钥 ID */
    private String accessKeyId;

    /** 访问密钥口令 */
    private String accessKeySecret;

    /** 访问域名 */
    private String endpoint;

    /** 实例名称 */
    private String instanceName;
}
