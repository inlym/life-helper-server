package com.weutil.account.login.phone.exception;

/**
 * 手机号和验证码不匹配异常
 *
 * <h2>使用说明
 * <p>包含以下2种情况，都抛出这个异常：
 * <p>1. 发了验证码，但是两者不匹配。
 * <p>2. 没发验证码。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/13
 * @since 2.3.0
 **/
public class PhoneCodeNotMatchException extends RuntimeException {
    public PhoneCodeNotMatchException() {
        super();
    }
}
