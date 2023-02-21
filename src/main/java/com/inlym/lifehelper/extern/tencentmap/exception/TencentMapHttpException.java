package com.inlym.lifehelper.extern.tencentmap.exception;

/**
 * 腾讯位置服务通用 HTTP 请求服务异常
 *
 * <h2>主要用途
 * <p>除特定异常外，发起腾讯位置服务 HTTP 请求发生异常时，抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/2/21
 * @since 1.9.2
 **/
public class TencentMapHttpException extends RuntimeException {
    public TencentMapHttpException(String message) {
        super(message);
    }
}
