package com.inlym.lifehelper.common.auth.simpletoken.exception;

/**
 * 无效令牌异常
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/27
 * @since 1.7.0
 **/
public class InvalidSimpleTokenException extends RuntimeException {
    public InvalidSimpleTokenException(String message) {
        super(message);
    }

    public static InvalidSimpleTokenException of(String token) {
        String message = "Invalid Token: " + token;
        return new InvalidSimpleTokenException(message);
    }
}
