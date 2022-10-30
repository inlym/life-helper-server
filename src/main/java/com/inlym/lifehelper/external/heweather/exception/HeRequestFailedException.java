package com.inlym.lifehelper.external.heweather.exception;

/**
 * 和风天气 HTTP 请求错误异常
 *
 * <h2>主要用途
 * <p>在发起和风天气 HTTP 请求时，请求错误则抛出此异常。
 *
 * <h2>备注
 * <p>在发起和风天气 HTTP 请求时，请求错误则抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/9/26
 * @since 1.4.0
 **/
public class HeRequestFailedException extends RuntimeException {
    public HeRequestFailedException(String message) {
        super(message);
    }

    public static HeRequestFailedException create(String apiName, String url, String code) {
        String message = apiName + " " + code + " " + url;
        return new HeRequestFailedException(message);
    }
}
