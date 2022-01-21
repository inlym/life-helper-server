package com.inlym.lifehelper.common.config;

import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.common.exception.WeixinInvalidAccessTokenException;
import com.inlym.lifehelper.common.model.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @since 2022-01-17 22:31
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 对外 HTTP 请求异常处理
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ExternalHttpRequestException.class)
    public ExceptionResponse handleExternalHttpRequestException(ExternalHttpRequestException e) {
        log.error(String.valueOf(e));
        return new ExceptionResponse(50001, "内部错误");
    }

    /**
     * 微信服务端接口调用凭据失效异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(WeixinInvalidAccessTokenException.class)
    public ExceptionResponse handleWeixinInvalidAccessTokenException(WeixinInvalidAccessTokenException e) {
        log.info("微信服务端接口调用凭证异常，错误信息：" + e);
        return new ExceptionResponse(50002, "内部错误");
    }

    /**
     * 通用异常处理
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionResponse handler(Exception e) {
        log.error(e.getMessage());
        return new ExceptionResponse(50000, "内部错误");
    }
}
