package com.inlym.lifehelper.external.wechat.exception;

/**
 * 微信服务端 HTTP 请求通用异常
 *
 * <h2>主要用途
 * <p>一些非特殊的异常，没必要逐个建立异常类，直接使用当前类即可。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/3
 * @since 1.3.0
 **/
public class WeChatCommonException extends RuntimeException {
    public WeChatCommonException(String message) {
        super(message);
    }

    public static WeChatCommonException create(int errorCode, String errorMessage) {
        String message = "errCode=" + errorCode + ", errMsg=" + errorMessage;
        return new WeChatCommonException(message);
    }
}
