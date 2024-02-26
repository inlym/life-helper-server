package com.inlym.lifehelper.login.qrcode.exception;

/**
 * 扫码登录凭据未找到异常
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/26
 * @since 2.2.0
 **/
public class LoginTicketNotFoundException extends RuntimeException {
    public LoginTicketNotFoundException(String message) {
        super(message);
    }
}
