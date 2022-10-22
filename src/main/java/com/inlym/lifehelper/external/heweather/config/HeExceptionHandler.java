package com.inlym.lifehelper.external.heweather.config;

import com.inlym.lifehelper.common.model.ExceptionResponse;
import com.inlym.lifehelper.external.heweather.exception.HeRequestFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 和风天气异常处理器
 *
 * <h2>主要用途
 * <p>发生和风天气 API 请求发生错误时，由当前处理器捕获并处理。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/9/26
 * @since 1.4.0
 **/
@RestControllerAdvice
@Slf4j
@Order(21)
@RequiredArgsConstructor
public class HeExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HeRequestFailedException.class)
    public ExceptionResponse handleHeRequestFailedException(HeRequestFailedException e) {
        log.error(e.getMessage());
        return ExceptionResponse.forServerError();
    }
}
