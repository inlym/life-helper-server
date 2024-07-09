package com.weutil.external.wemap.exception;

/**
 * 腾讯位置服务 API 调用异常
 * <p>
 * <h2>主要用途
 * <p>文档见：<a href="https://lbs.qq.com/service/webService/webServiceGuide/status">状态码说明</a>
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/21
 * @since 2.2.0
 **/
public class WeMapApiException extends RuntimeException {
    public WeMapApiException(String message) {
        super(message);
    }
}
