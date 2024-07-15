package com.weutil.sms.config;

import com.weutil.common.model.ErrorResponse;
import com.weutil.sms.exception.SmsCommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 短信模块异常处理器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/16
 * @since 3.0.0
 **/
@RestControllerAdvice
@Slf4j
@Order(50)
public class SmsExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SmsCommonException.class)
    public ErrorResponse handleSmsCommonException(SmsCommonException e) {
        return new ErrorResponse(10, "短信发送失败，请稍后再试！");
    }
}
