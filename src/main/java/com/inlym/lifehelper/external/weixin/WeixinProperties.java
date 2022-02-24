package com.inlym.lifehelper.external.weixin;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信小程序配置信息，用于与微信服务器交互
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-23
 * @since 1.0.0
 */
@Component
@ConfigurationProperties(prefix = "lifehelper.weixin")
@Data
public class WeixinProperties {
    /** 小程序 appId */
    private String appid;

    /** 小程序 appSecret */
    private String secret;
}
