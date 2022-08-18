package com.inlym.lifehelper.common.base.aliyun.ots.widecolumn;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 宽表模型配置
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/13
 * @since 1.3.0
 **/
@Component
@ConfigurationProperties(prefix = "aliyun.ots.wide-column")
@Data
public class WideColumnProperties {
    /** 访问密钥 ID */
    private String accessKeyId;

    /** 访问密钥口令 */
    private String accessKeySecret;

    /** 访问域名 */
    private String endpoint;

    /** 实例名称 */
    private String instanceName;
}
