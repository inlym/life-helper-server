package com.inlym.lifehelper.common.exception;

import lombok.Getter;
import lombok.ToString;

/**
 * 对外 HTTP 请求异常
 *
 * <h2>说明
 *
 * <p>部分服务依赖第三方 API，向其发送 HTTP 请求时，可能发生异常，这里的异常指的是未返回我方预期获取的数据。大部分错误是由于我方传递了非法的参数，
 * 极少部分才是第三方服务异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-18
 * @since 1.0.0
 **/
@ToString
public class ExternalHttpRequestException extends Exception {
    /** 第三方 API 名称（中文描述） */
    @Getter
    private final String name;

    /** 发起 HTTP 请求的 URL 地址 */
    @Getter
    private final String url;

    /** 错误状态码 */
    @Getter
    private final String code;

    /** 错误消息 */
    @Getter
    private final String message;

    public ExternalHttpRequestException(String name, String url, String code, String message) {
        super();

        this.name = name;
        this.url = url;
        this.code = code;
        this.message = message;
    }

    public ExternalHttpRequestException(String name, String url, String code) {
        this(name, url, code, "");
    }

    public ExternalHttpRequestException(String name, String url, Integer code, String message) {
        this(name, url, String.valueOf(code), message);
    }
}
