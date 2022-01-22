package com.inlym.lifehelper.common.config;

import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.common.model.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理器
 *
 * @author inlym
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
     * 数据校验不通过异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ExceptionResponse handleConstraintViolationException(ConstraintViolationException e) {
        return new ExceptionResponse(40000, "缺少参数或参数格式不正确");
    }

    /**
     * 通用异常处理
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionResponse handler(Exception e) {
        log.error(e.getMessage());
        return new ExceptionResponse(50000, "服务器内部错误");
    }
}
