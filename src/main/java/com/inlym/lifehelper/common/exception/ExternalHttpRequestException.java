package com.inlym.lifehelper.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 对外 HTTP 请求异常
 * <p>
 * [描述]
 * 部分服务依赖第三方 API，向其发送 HTTP 请求时，可能发生异常，这里的异常指的是未返回我方预期获取的数据。大部分错误是由于我方传递了非法的参数，
 * 极少部分才是第三方服务异常。
 *
 * @author inlym
 * @since 2022-01-18 00:11
 **/
public class ExternalHttpRequestException extends Exception {
    /** 第三方 API 名称（中文描述） */
    @Getter
    @Setter
    private String name;

    /** 发起 HTTP 请求的 URL 地址 */
    @Getter
    @Setter
    private String url;

    /** 错误状态码 */
    @Getter
    @Setter
    private String errCode;

    /** 错误消息 */
    @Getter
    @Setter
    private String errMessage;

    public ExternalHttpRequestException(String name, String url, String errCode, String errMessage) {
        super("[" + name + "] 请求异常, url=" + url + ", errCode=" + errCode + ", errMessage=" + errMessage);

        this.name = name;
        this.url = url;
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public ExternalHttpRequestException(String name, String url, int errCode, String errMessage) {
        this(name, url, String.valueOf(errCode), errMessage);
    }
}
