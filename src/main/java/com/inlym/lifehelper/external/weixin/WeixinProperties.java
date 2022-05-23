package com.inlym.lifehelper.external.weixin;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * 微信小程序配置信息，用于与微信服务器交互
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-23
 * @since 1.0.0
 */
@Component
@Validated
@ConfigurationProperties(prefix = "lifehelper.weixin")
@Data
public class WeixinProperties {
    /** 小程序 appId */
    @NotEmpty
    private String appid;

    /** 小程序 appSecret */
    @NotEmpty
    private String secret;
}
