package com.inlym.lifehelper.common.config;

import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.common.exception.UnauthorizedAccessException;
import com.inlym.lifehelper.common.model.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理器
 *
 * <h2>主要用途
 * <p>捕获项目内抛出的错误，直接返回对应的响应内容。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2021-12-27
 * @since 1.0.0
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
     * 请求参数，必填项未传值
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ExceptionResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return new ExceptionResponse(40001, "缺少请求参数 " + e.getParameterName());
    }

    /**
     * 数据校验不通过异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ExceptionResponse handleConstraintViolationException(ConstraintViolationException e) {
        ConstraintViolationImpl<?> cv = (ConstraintViolationImpl<?>) e
            .getConstraintViolations()
            .toArray()[0];

        String property = String
            .valueOf(cv.getPropertyPath())
            .split("\\.")[1];

        String message = cv.getMessage();

        return new ExceptionResponse(40002, property + " " + message);
    }

    /**
     * 鉴权异常处理（即需要登录的接口未提供有效的鉴权信息）
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class )
    public ExceptionResponse handleAccessDeniedException(AccessDeniedException e) {
        return new ExceptionResponse(40010, "未登录或登录信息错误");
    }

    /**
     * 鉴权异常处理（即需要登录的接口未提供有效的鉴权信息）
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler( UnauthorizedAccessException.class)
    public ExceptionResponse handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        return new ExceptionResponse(40010, "未登录或登录信息错误");
    }

    /**
     * 通用异常处理
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionResponse handler(Exception e) {
        log.debug(e
            .getClass()
            .getName() + ":" + e.getMessage());
        return new ExceptionResponse(50000, "服务器内部错误");
    }
}
