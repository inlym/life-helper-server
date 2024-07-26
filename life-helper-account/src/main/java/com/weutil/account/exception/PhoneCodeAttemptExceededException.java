package com.weutil.account.exception;

/**
 * 短信验证码尝试次数超出限制异常
 *
 * <h2>说明
 * <p>目前限定5次
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/26
 * @since 3.0.0
 **/
public class PhoneCodeAttemptExceededException extends RuntimeException {}
