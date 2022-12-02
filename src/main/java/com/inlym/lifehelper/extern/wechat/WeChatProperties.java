package com.inlym.lifehelper.extern.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * 微信小程序服务端配置信息
 *
 * <h2>说明
 * <p>小程序账号信息。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/3
 * @since 1.3.0
 **/
@Component
@ConfigurationProperties(prefix = "lifehelper.wechat")
@Validated
@Data
public class WeChatProperties {
    /** 小程序 appId */
    @NotEmpty
    private String appid;

    /** 小程序 appSecret */
    @NotEmpty
    private String secret;
}
