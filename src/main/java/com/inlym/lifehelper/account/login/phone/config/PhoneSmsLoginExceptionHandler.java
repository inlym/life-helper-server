package com.inlym.lifehelper.account.login.phone.config;

import com.inlym.lifehelper.account.login.phone.exception.*;
import com.inlym.lifehelper.account.login.phone.model.SmsRateLimitExceededExceptionResponse;
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
public class PhoneSmsLoginExceptionHandler {
    /** 短信发送失败提示文案 */
    private static final String SENT_FAILURE_TIP = "短信发送失败，请稍后再试";

    /** 验证码错误提示文案 */
    private static final String PHONE_CODE_WRONG_TIP = "您输入的验证码不正确，请重新输入";

    /** 设备异常提示文案 */
    private static final String ABNORMAL_DEVICE_TIP = "您当前的设备所处环境异常，请稍后再试";

    // ============================ 短信发送环节异常 ============================

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SmsSentFailureException.class)
    public ErrorResponse handleSmsSentFailureException() {
        return new ErrorResponse(10010, SENT_FAILURE_TIP);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SmsRateLimitExceededException.class)
    public SmsRateLimitExceededExceptionResponse handleSmsRateLimitExceededException(SmsRateLimitExceededException exception) {
        return new SmsRateLimitExceededExceptionResponse(10011, SENT_FAILURE_TIP, exception.getRemainingSeconds());
    }

    // ============================ 短信验证环节异常 ============================

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotSameIpException.class)
    public ErrorResponse handleNotSameIpException() {
        return new ErrorResponse(10021, ABNORMAL_DEVICE_TIP);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(PhoneCodeExpiredException.class)
    public ErrorResponse handlePhoneCodeExpiredException() {
        return new ErrorResponse(10020, PHONE_CODE_WRONG_TIP);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(PhoneCodeNotMatchException.class)
    public ErrorResponse handlePhoneCodeNotMatchException() {
        return new ErrorResponse(10020, PHONE_CODE_WRONG_TIP);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SmsCheckCodeNotExistsException.class)
    public ErrorResponse handleSmsCheckCodeNotExistsException() {
        return new ErrorResponse(10020, PHONE_CODE_WRONG_TIP);
    }
}
