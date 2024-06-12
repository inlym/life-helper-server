package com.inlym.lifehelper.account.login.qrcode.config;

import com.inlym.lifehelper.account.login.qrcode.exception.LoginTicketNotFoundException;
import com.inlym.lifehelper.account.login.qrcode.model.LoginByQrCodeResultVO;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 扫码登录模块异常处理器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/26
 * @since 2.2.0
 **/
@RestControllerAdvice
@Order(10)
public class QrcodeLoginExceptionHandler {
    /**
     * 捕获扫码登录凭据未找到异常并处理
     *
     * @date 2024/2/26
     * @since 2.2.0
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(LoginTicketNotFoundException.class)
    public LoginByQrCodeResultVO handleLoginTicketNotFoundException(LoginTicketNotFoundException e) {
        return LoginByQrCodeResultVO.builder().status(4).build();
    }
}
