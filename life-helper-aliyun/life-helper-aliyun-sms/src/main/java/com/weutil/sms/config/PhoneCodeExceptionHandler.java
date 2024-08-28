package com.weutil.sms.config;

import com.weutil.common.model.ErrorResponse;
import com.weutil.sms.exception.*;
import com.weutil.sms.model.SmsRateLimitExceededExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 短信验证码模块异常捕获器
 *
 * <h2>说明
 * <p>说明文本内容
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/8/28
 * @since 3.0.0
 **/
@RestControllerAdvice
@Slf4j
@Order(50)
public class PhoneCodeExceptionHandler {

    // ============================ 短信发送环节错误 ============================

    /**
     * 处理手机号异常问题
     *
     * <h3>异常说明
     * <p>[实际原因] 用户输入的手机号格式未通过校验（即不是一个正常的手机号）
     * <p>[提示文案] 你输入的手机号不正确，请重新输入
     * <p>[前端使用] 无需额外处理，直接展示提示文案
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({InvalidPhoneNumberException.class})
    public ErrorResponse handleInvalidPhoneNumberException() {
        return new ErrorResponse(11001, "你输入的手机号不正确，请重新输入");
    }

    /**
     * 处理短信发送失败问题
     *
     * <h3>异常说明
     * <p>[实际原因] 短信验证码发送，由 API 返回的结果告知发送失败
     * <p>[提示文案] 短信发送失败，请稍后再试
     * <p>[前端使用] 无需额外处理，直接展示提示文案
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({SmsSentFailureException.class})
    public ErrorResponse handleSmsSentFailureException() {
        return new ErrorResponse(11002, "短信发送失败，请稍后再试");
    }

    /**
     * 处理短信发送超频问题
     *
     * <h3>异常说明
     * <p>[实际原因] 短信发送超过了频次限制（1条/1分钟 & 5条/1小时）
     * <p>[提示文案] 你获取验证码的次数超过限制，请稍后再试
     * <p>[前端使用] 自行拼接“剩余秒数”字段形成文案展示在按钮上，给出的提示文案作为保底方案
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({SmsRateLimitExceededException.class})
    public ErrorResponse handleSmsRateLimitExceededException(SmsRateLimitExceededException exception) {
        return new SmsRateLimitExceededExceptionResponse(11003, "你获取验证码的次数超过限制，请稍后再试", exception.getRemainingSeconds());
    }

    // ============================ 验证码校验环节错误 ============================

    /**
     * 处理校验码不存在问题
     *
     * <h3>异常说明
     * <p>[实际原因] 大概率由攻击者发起，计划使用“碰撞”通过校验，由于使用了32位随机字符串代替手机号进行匹配，因此安全性较高
     * <p>[提示文案] 验证码错误，请重新输入
     * <p>[前端使用] 无需额外处理，直接展示提示文案
     * <p>[备注] 为安全起见，此项返回了模糊的提示文案，未告知真实的错误原因
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({SmsCheckTicketNotExistsException.class})
    public ErrorResponse handleSmsCheckTicketNotExistsException() {
        return new ErrorResponse(11011, "验证码错误，请重新输入");
    }

    /**
     * 处理验证码不正确问题
     *
     * <h3>异常说明
     * <p>[实际原因] 验证码输入错误
     * <p>[提示文案] 验证码错误，请重新输入
     * <p>[前端使用] 无需额外处理，直接展示提示文案
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({PhoneCodeNotMatchException.class})
    public ErrorResponse handlePhoneCodeNotMatchException() {
        return new ErrorResponse(11011, "验证码错误，请重新输入");
    }

    /**
     * 处理验证码已过期问题
     *
     * <h3>异常说明
     * <p>[实际原因] 验证码已过期（有效期为5分钟）
     * <p>[提示文案] 验证码已过期，请重新获取验证码
     * <p>[前端使用] 无需额外处理，直接展示提示文案
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({PhoneCodeExpiredException.class})
    public ErrorResponse handlePhoneCodeExpiredException() {
        return new ErrorResponse(11012, "验证码已过期，请重新获取验证码");
    }

    /**
     * 处理验证码已使用问题
     *
     * <h3>异常说明
     * <p>[实际原因] 验证码已通过校验并获取了登录凭证
     * <p>[提示文案] 验证码已过期，请重新获取验证码
     * <p>[前端使用] 无需额外处理，直接展示提示文案
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({PhoneCodeAlreadyUsedException.class})
    public ErrorResponse handlePhoneCodeAlreadyUsedException() {
        return new ErrorResponse(11012, "验证码已过期，请重新获取验证码");
    }

    /**
     * 处理验证码输入错误太多问题
     *
     * <h3>异常说明
     * <p>[实际原因] 验证码输入错误的次数超过了限制（10次）
     * <p>[提示文案] 验证码已过期，请重新获取验证码
     * <p>[前端使用] 无需额外处理，直接展示提示文案
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({PhoneCodeAttemptExceededException.class})
    public ErrorResponse handlePhoneCodeAttemptExceededException() {
        return new ErrorResponse(11013, "你输入的验证码错误次数过多，验证码已失效，请重新获取验证码");
    }

    /**
     * 处理登录设备与获取验证码设备的 IP 地址不一致问题
     *
     * <h3>异常说明
     * <p>[实际原因] 获取验证码操作的客户端与“验证”操作的客户端对应的 IP 地址不一致
     * <p>[提示文案] 由于网络环境变化，你的验证码已失效，请重新获取验证码
     * <p>[备注] 为安全起见，此项返回了模糊的提示文案，未告知真实的错误原因
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({NotSameIpException.class})
    public ErrorResponse handleNotSameIpException() {
        return new ErrorResponse(11014, "由于网络环境变化，你的验证码已失效，请重新获取验证码");
    }
}
