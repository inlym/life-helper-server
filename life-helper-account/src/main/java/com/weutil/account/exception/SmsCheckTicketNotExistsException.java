package com.weutil.account.exception;

/**
 * 短信校验码不存在异常
 *
 * <h2>异常触发条件
 * <p>用户无法手动接触 {@code CheckCode}, 在前端测试充分的前提下，触发该异常一般是攻击者在伪造请求尝试进行碰撞攻击导致的。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
public class SmsCheckTicketNotExistsException extends RuntimeException {}
