package com.weutil.aliyun.apigw.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云 API 网关配置
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/11/18
 * @see <a href="https://help.aliyun.com/zh/api-gateway/traditional-api-gateway/user-guide/jwt-authentication">JWT认证插件</a>
 * @since 3.0.0
 **/
@Component
@ConfigurationProperties(prefix = "aliyun.apigw")
@Data
public class AliyunApigwProperties {
    /** 唯一的 ID 编号 */
    private String kid;

    /** X.509 PEM 格式的私钥文本 */
    private String privateKey;
}
