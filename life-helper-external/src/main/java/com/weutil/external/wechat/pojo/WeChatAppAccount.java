package com.weutil.external.wechat.pojo;

import lombok.Data;

/**
 * 微信应用账号信息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/16
 * @since 2.1.0
 **/
@Data
public class WeChatAppAccount {
    private String appId;

    private String appSecret;
}
