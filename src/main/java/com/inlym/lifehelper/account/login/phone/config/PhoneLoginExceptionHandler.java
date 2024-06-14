package com.inlym.lifehelper.account.login.phone.config;

import com.inlym.lifehelper.account.login.phone.exception.PhoneCodeNotMatchException;
import com.inlym.lifehelper.common.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 手机号登录模块异常处理器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/13
 * @since 2.3.0
 **/
@RestControllerAdvice
@Slf4j
@Order(10)
public class PhoneLoginExceptionHandler {
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(PhoneCodeNotMatchException.class)
    public ErrorResponse handlePhoneCodeNotMatchException() {
        return new ErrorResponse(10010, "您输入的验证码不正确，请重新输入！");
    }
}
