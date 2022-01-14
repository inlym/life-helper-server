package com.inlym.lifehelper.external.hefeng;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hefeng")
@Data
public class HefengProperties {
    /** 商业版请求 URL */
    private String proUrl;

    /** 开发版请求 URL */
    private String devUrl;

    /** 商业版密钥 */
    private String proKey;

    /** 开发版密钥 */
    private String devKey;
}
