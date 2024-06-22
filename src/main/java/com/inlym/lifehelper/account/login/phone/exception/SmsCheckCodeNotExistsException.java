package com.inlym.lifehelper.account.login.phone.exception;

/**
 * 短信校验码不存在异常
 *
 * <h2>异常触发条件
 * <p>用户无法手动接触 {@code CheckCode}, 在前端测试充分的前提下，触发该异常一般是攻击者在伪造请求尝试进行碰撞攻击导致的。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/22
 * @since 2.3.0
 **/
public class SmsCheckCodeNotExistsException extends RuntimeException {
    public SmsCheckCodeNotExistsException() {
        super();
    }
}
