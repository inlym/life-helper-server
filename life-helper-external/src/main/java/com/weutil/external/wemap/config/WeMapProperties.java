package com.weutil.external.wemap.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 腾讯位置服务配置信息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/21
 * @since 2.1.0
 **/
@Component
@ConfigurationProperties(prefix = "lifehelper.wemap")
@Data
public class WeMapProperties {
    /** 开发者密钥 */
    private String key;
}
