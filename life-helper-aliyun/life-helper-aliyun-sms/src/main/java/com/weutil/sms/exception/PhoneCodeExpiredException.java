package com.weutil.sms.exception;

/**
 * 短信验证码已过期异常
 *
 * <h2>触发条件
 * <p>当前距离短信发送超过了指定时长，认定验证码失效。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
public class PhoneCodeExpiredException extends RuntimeException {}
