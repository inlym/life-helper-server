package com.inlym.lifehelper.login.qrcode.exception;

import com.inlym.lifehelper.login.qrcode.entity.QrCodeTicket;
import com.inlym.lifehelper.login.qrcode.pojo.QrCodeTicketVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 扫码登录异常处理器
 *
 * <h2>主要用途
 * <p>捕获扫码登录模块内抛出的错误，直接返回对应的响应内容。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/7
 * @since 1.3.0
 **/
@RestControllerAdvice
@Slf4j
@Order(1000)
public class QrCodeLoginExceptionHandler {
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(InvalidQrCodeTicketException.class)
    public QrCodeTicketVO handleInvalidQrCodeTicketException(InvalidQrCodeTicketException e) {
        log.trace(String.valueOf(e));

        return QrCodeTicketVO
            .builder()
            .status(QrCodeTicket.Status.INVALID)
            .build();
    }
}
