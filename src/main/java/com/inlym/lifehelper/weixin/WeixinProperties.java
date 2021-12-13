package com.inlym.lifehelper.weixin;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信（小程序）配置信息，用于与微信服务器交互
 */
@Component
@ConfigurationProperties(prefix = "weixin")
@Data
public class WeixinProperties {
    /**
     * 小程序 appId
     */
    private String appid;

    /**
     * 小程序 appSecret
     */
    private String secret;
}
