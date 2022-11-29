package com.inlym.lifehelper.extern.wechat.exception;

/**
 * 微信服务端无效接口调用凭证异常
 *
 * <h2>说明
 * <p>在前期接口调通的前提下，项目运行中出现错误大概率都是 `access_token` 失效，因此将该错误单独处理。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/4
 * @since 1.3.0
 **/
public class WeChatInvalidAccessTokenException extends RuntimeException {
    public WeChatInvalidAccessTokenException(String message) {
        super(message);
    }

    public static WeChatInvalidAccessTokenException create(String token) {
        String message = "Invalid Token: " + token;
        return new WeChatInvalidAccessTokenException(message);
    }
}
