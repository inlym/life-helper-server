package com.inlym.lifehelper.login.qrcode2.config;

import com.inlym.lifehelper.common.model.ExceptionResponse;
import com.inlym.lifehelper.login.qrcode2.exception.QrCodeTicketNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 扫码登录模块异常捕获器
 *
 * <h2>主要用途
 * <p>捕获并处理「扫码登录」模块的异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/5/16
 * @since 2.0.0
 **/
@RestControllerAdvice
@Slf4j
@Order(301)
public class QrCodeLoginExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(QrCodeTicketNotFoundException.class)
    public ExceptionResponse handle(QrCodeTicketNotFoundException exception) {
        log.trace(exception.getMessage());
        return new ExceptionResponse(30101, "你操作的小程序码已失效，请刷新后重试！");
    }
}
