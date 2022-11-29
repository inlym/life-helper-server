package com.inlym.lifehelper.extern.wechat.exception;

import com.inlym.lifehelper.common.model.ExceptionResponse;
import com.inlym.lifehelper.extern.wechat.WeChatAccessTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 微信服务端异常处理器
 *
 * <h2>主要用途
 * <p>捕获微信服务端 API 接口调用异常，并进行处理和返回对应的响应内容。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/8
 * @since 1.3.0
 **/
@RestControllerAdvice
@Slf4j
@Order(20)
@RequiredArgsConstructor
public class WeChatExceptionHandler {
    private final WeChatAccessTokenService weChatAccessTokenService;

    /**
     * 处理微信服务端接口调用凭证异常
     *
     * @since 1.3.0
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(WeChatInvalidAccessTokenException.class)
    public ExceptionResponse handleWeChatInvalidAccessTokenException(WeChatInvalidAccessTokenException e) {
        log.error(e.getMessage());
        weChatAccessTokenService.refreshAsync();
        return ExceptionResponse.forServerError();
    }
}
