package com.weutil.common.config;

import com.weutil.common.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

/**
 * 根异常处理器
 *
 * <h2>说明
 * <p>为方便调试，在开发环境不会将 {@code Exception} 捕获而是直接报错抛出异常堆栈，仅在生产环境，才返回通用提示处理。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/22
 * @since 2.3.0
 **/
@RestControllerAdvice
@Slf4j
@Order(1000000)
@Profile("prod")
public class RootExceptionHandler {
    /** 通用错误提示 */
    private static final String COMMON_ERROR_MESSAGE = "网络异常，请稍后再试！";

    /**
     * 通用异常处理
     *
     * <h2>说明
     * <p>未被其他处理器捕获时，最终会进入到这里处理。
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        log.error("[Exception] {}: {}", e.getClass(), e.getMessage());
        log.trace("{}", Arrays.toString(e.getStackTrace()));
        return new ErrorResponse(1, COMMON_ERROR_MESSAGE);
    }
}
