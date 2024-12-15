package com.weutil.aliyun.captcha.config;

import com.weutil.aliyun.captcha.exception.AliyunCaptchaVerifiedFailureException;
import com.weutil.common.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 阿里云验证码模块异常处理器
 *
 * <h3>错误码范围
 * <p>{@code 12101} ~ {@code 12199}
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/15
 * @since 3.0.0
 **/
@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 1000)
public class AliyunCaptchaExceptionHandler {
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(AliyunCaptchaVerifiedFailureException.class)
    public ErrorResponse handleAliyunCaptchaVerifiedFailureException() {
        return new ErrorResponse(12101, "验证码校验未通过");
    }
}
