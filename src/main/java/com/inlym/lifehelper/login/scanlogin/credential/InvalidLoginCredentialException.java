package com.inlym.lifehelper.login.scanlogin.credential;

/**
 * 无效登录凭证异常
 *
 * <h2>说明
 * <p>在对登录凭证进行操作和状态变更时，会预设操作前的状态值，若状态值不正确则抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/28
 * @since 1.3.0
 **/
public class InvalidLoginCredentialException extends RuntimeException {
    public InvalidLoginCredentialException(String message) {
        super(message);
    }

    public static InvalidLoginCredentialException defaultMessage() {
        return new InvalidLoginCredentialException("登录凭证无效");
    }
}
