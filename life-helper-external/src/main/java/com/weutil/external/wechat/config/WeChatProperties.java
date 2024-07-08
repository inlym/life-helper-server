package com.weutil.external.wechat.config;

import com.weutil.external.wechat.pojo.WeChatAppAccount;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
    /** 【小鸣助手】小程序配置 */
    private WeChatAppAccount mainApp;

    /** 【小鸣 AI】小程序配置 */
    private WeChatAppAccount aiApp;

    /** 【小鸣天气】小程序配置 */
    private WeChatAppAccount weatherApp;
}
