package com.weutil.sms.exception;

/**
 * 短信验证码已被使用异常
 *
 * <h2>说明
 * <p>“手机号+短信验证码”校验通过后，发放登录凭证，再次使用相同短信验证码登录时，将抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/26
 * @since 3.0.0
 **/
public class PhoneCodeAlreadyUsedException extends RuntimeException {}
