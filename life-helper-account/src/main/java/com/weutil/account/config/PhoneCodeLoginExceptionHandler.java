package com.weutil.account.config;

import com.weutil.account.exception.NotSameIpException;
import com.weutil.account.exception.PhoneCodeAttemptExceededException;
import com.weutil.account.exception.PhoneCodeNotMatchException;
import com.weutil.common.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 短信验证码模块异常捕获器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/8/28
 * @since 3.0.0
 **/
@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 1000)
public class PhoneCodeLoginExceptionHandler {

    /**
     * 处理验证码不正确问题
     *
     * <h3>异常说明
     * <p>[实际原因] 验证码输入错误
     * <p>[前端使用] 无需额外处理，直接展示提示文案
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({PhoneCodeNotMatchException.class})
    public ErrorResponse handlePhoneCodeNotMatchException() {
        return new ErrorResponse(11011, "验证码错误，请重新输入");
    }

    /**
     * 处理验证码输入错误太多问题
     *
     * <h3>异常说明
     * <p>[实际原因] 验证码输入错误的次数超过了限制（10次）
     * <p>[前端使用] 无需额外处理，直接展示提示文案
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({PhoneCodeAttemptExceededException.class})
    public ErrorResponse handlePhoneCodeAttemptExceededException() {
        return new ErrorResponse(11013, "你输入的验证码错误次数过多，已限制您的操作，请5分钟后再试");
    }

    /**
     * 处理登录设备与获取验证码设备的 IP 地址不一致问题
     *
     * <h3>异常说明
     * <p>[实际原因] 获取验证码操作的客户端与“验证”操作的客户端对应的 IP 地址不一致
     * <p>[备注] 为安全起见，此项返回了模糊的提示文案，未告知真实的错误原因
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({NotSameIpException.class})
    public ErrorResponse handleNotSameIpException() {
        return new ErrorResponse(11014, "由于网络环境变化，你的验证码已失效，请重新获取验证码");
    }
}
