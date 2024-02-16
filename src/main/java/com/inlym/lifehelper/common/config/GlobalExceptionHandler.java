package com.inlym.lifehelper.common.config;

import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.common.exception.UnauthorizedAccessException;
import com.inlym.lifehelper.common.exception.UnauthorizedResourceAccessException;
import com.inlym.lifehelper.common.model.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

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
@Order(1000)
public class GlobalExceptionHandler {
    /**
     * 对外 HTTP 请求异常处理
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ExternalHttpRequestException.class)
    public ErrorResponse handleExternalHttpRequestException(ExternalHttpRequestException e) {
        log.error(String.valueOf(e));
        return new ErrorResponse(5000, "内部错误");
    }

    /**
     * 请求参数，必填项未传值
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return new ErrorResponse(4, "缺少请求参数 " + e.getParameterName());
    }

    /**
     * 处理 {@code @Valid} 抛出的异常
     *
     * @since 1.3.0
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult exceptions = e.getBindingResult();

        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);

                return new ErrorResponse(3, fieldError.getDefaultMessage());
            }
        }

        return new ErrorResponse(3, "请求数据错误");
    }

    /**
     * 处理 {@code @Validated} 抛出的异常
     *
     * @since 1.3.0
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        ConstraintViolationImpl<?> cv = (ConstraintViolationImpl<?>) e
            .getConstraintViolations()
            .toArray()[0];

        String message = cv.getMessage();

        return new ErrorResponse(3, message);
    }

    /**
     * 鉴权异常处理（即需要登录的接口未提供有效的鉴权信息）
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AccessDeniedException.class, UnauthorizedAccessException.class})
    public ErrorResponse handleAccessDeniedException() {
        return new ErrorResponse(2, "请先登录再操作！");
    }

    /**
     * 访问了不属于自己的资源
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({UnauthorizedResourceAccessException.class})
    public ErrorResponse handleUnauthorizedResourceAccessException() {
        return new ErrorResponse(4, "当前资源已失效，请稍后再试！");
    }

    /**
     * 通用异常处理
     *
     * <h2>说明
     * <p>未被其他处理器捕获时，最终会进入到这里处理。
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        log.debug(e
                      .getClass()
                      .getName() + ":" + e.getMessage());
        return new ErrorResponse(1);
    }
}
