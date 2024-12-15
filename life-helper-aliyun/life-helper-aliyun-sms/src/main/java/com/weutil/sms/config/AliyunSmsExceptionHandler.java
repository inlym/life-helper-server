package com.weutil.sms.config;

import com.weutil.common.model.ErrorResponse;
import com.weutil.sms.exception.InvalidPhoneNumberException;
import com.weutil.sms.exception.SmsRateLimitExceededException;
import com.weutil.sms.exception.SmsSentFailureException;
import com.weutil.sms.model.SmsRateLimitExceededExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 阿里云短信模块异常处理器
 *
 * <h3>错误码范围
 * <p>{@code 12301} ~ {@code 12399}
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/16
 * @since 3.0.0
 **/
@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 1000)
public class AliyunSmsExceptionHandler {

    // ================================================== 发送前校验环节 ==================================================

    /**
     * 处理手机号异常问题
     *
     * <h3>异常说明
     * <p>[实际原因] 用户输入的手机号格式未通过校验（即不是一个正常的手机号）
     * <p>[前端使用] 无需额外处理，直接展示提示文案
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidPhoneNumberException.class})
    public ErrorResponse handleInvalidPhoneNumberException() {
        return new ErrorResponse(12301, "你输入的手机号不正确，请重新输入");
    }

    /**
     * 处理短信发送超频问题
     *
     * <h3>异常说明
     * <p>[实际原因] 短信发送超过了频次限制（1条/1分钟 & 5条/1小时）
     * <p>[前端使用] 自行拼接“剩余秒数”字段形成文案展示在按钮上，给出的提示文案作为保底方案
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({SmsRateLimitExceededException.class})
    public ErrorResponse handleSmsRateLimitExceededException(SmsRateLimitExceededException exception) {
        return new SmsRateLimitExceededExceptionResponse(12303, "你获取验证码的次数超过限制，请稍后再试", exception.getRemainingSeconds());
    }

    // ================================================== 发送环节 ==================================================

    /**
     * 处理短信发送失败问题
     *
     * <h3>异常说明
     * <p>[实际原因] 短信验证码发送，由 API 返回的结果告知发送失败
     * <p>[前端使用] 无需额外处理，直接展示提示文案
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({SmsSentFailureException.class})
    public ErrorResponse handleSmsSentFailureException() {
        return new ErrorResponse(12302, "短信发送失败，请稍后再试");
    }
}
