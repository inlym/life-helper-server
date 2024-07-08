package com.weutil.account.login.phone.exception;

/**
 * 非同IP异常
 *
 * <h2>说明
 * <p>主要用途是防“中间人”攻击。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/23
 * @since 2.3.0
 **/
public class NotSameIpException extends RuntimeException {
    public NotSameIpException() {
        super();
    }
}
