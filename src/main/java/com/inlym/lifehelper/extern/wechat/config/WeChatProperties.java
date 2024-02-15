package com.inlym.lifehelper.extern.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

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
@Data
public class WeChatProperties {
    /** 多个小程序映射集（appId:appSecret） */
    private HashMap<String, String> miniprogramMap;
}
