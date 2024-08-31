package com.weutil.user.exception;

/**
 * 用户不存在异常
 *
 * <h2>使用说明
 * <p>当经过层层判断和处理后，假定某处查询的用户必定存在，而实际查询不存在时，抛出该异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
public class UserNotExistException extends RuntimeException {}
