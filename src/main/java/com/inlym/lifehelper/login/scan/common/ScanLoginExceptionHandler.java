package com.inlym.lifehelper.login.scan.common;

import com.inlym.lifehelper.common.model.ErrorResponse;
import com.inlym.lifehelper.login.scan.exception.ScanLoginTicketNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 扫码登录模块异常处理器
 *
 * <h2>主要用途
 * <p>捕获并处理「扫码登录」模块的异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/1/7
 * @since 1.9.0
 **/
@RestControllerAdvice
@Slf4j
@Order(32)
public class ScanLoginExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ScanLoginTicketNotFoundException.class)
    public ErrorResponse handleScanLoginTicketNotFoundException(ScanLoginTicketNotFoundException e) {
        log.trace(e.getMessage());
        return new ErrorResponse(32001, "你操作的小程序码已失效，请刷新后重试！");
    }
}
