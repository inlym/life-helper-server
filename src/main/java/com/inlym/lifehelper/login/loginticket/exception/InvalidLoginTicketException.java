package com.inlym.lifehelper.login.loginticket.exception;

/**
 * 无效登录凭据异常
 *
 * <h2>说明
 * <p>要查找的登录凭据不存在或对应状态错误时抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/7/1
 * @since 1.3.0
 **/
public class InvalidLoginTicketException extends RuntimeException {
    public InvalidLoginTicketException(String message) {
        super(message);
    }

    public static InvalidLoginTicketException defaultMessage() {
        return new InvalidLoginTicketException("登录凭证无效");
    }
}
