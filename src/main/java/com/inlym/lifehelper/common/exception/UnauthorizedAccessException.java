package com.inlym.lifehelper.common.exception;

/**
 * 未授权访问异常
 *
 * <h2>说明
 * <p>需要提供鉴权信息的 API，未提供或提供了无效的鉴权信息，则抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/4/20
 * @since 1.1.0
 **/
public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
