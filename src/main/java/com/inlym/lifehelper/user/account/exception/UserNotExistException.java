package com.inlym.lifehelper.user.account.exception;

/**
 * 用户不存在异常
 *
 * <h2>使用说明
 * <p>当经过层层判断和处理后，假定某处查询的用户必定存在，而实际查询不存在时，抛出该异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/1
 * @since 1.3.0
 **/
public class UserNotExistException extends RuntimeException {
    public UserNotExistException(String message) {
        super(message);
    }

    /**
     * 从用户 ID 创建
     *
     * <h2>使用说明
     * <p>要查询某个用户 ID 时，发现为空，则使用该方法创建异常。
     *
     * @param userId 用户 ID
     */
    public static UserNotExistException fromId(int userId) {
        String message = "用户 ID 为 " + userId + " 的用户不存在";
        return new UserNotExistException(message);
    }
}
