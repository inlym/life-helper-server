package com.inlym.lifehelper.extern.chatgptold.config;

import com.inlym.lifehelper.common.model.ExceptionResponse;
import com.inlym.lifehelper.extern.chatgptold.exception.ChatGPTCommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 类名称
 *
 * <h2>主要用途
 * <p>
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/25
 * @since 1.9.5
 **/
@RestControllerAdvice
@Slf4j
@Order(30)
public class ChatGPTExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ChatGPTCommonException.class)
    public ExceptionResponse handleCommonException(ChatGPTCommonException e) {
        return new ExceptionResponse(20101, "当前排队拥堵，请稍后再试！");
    }
}
