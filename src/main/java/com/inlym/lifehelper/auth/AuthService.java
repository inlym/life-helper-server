package com.inlym.lifehelper.auth;

/**
 * 权限相关服务
 */
public interface AuthService {
    /**
     * 为指定用户 ID 生成登录凭证
     *
     * @param userId 用户 ID
     */
    String createToken(int userId);

    /**
     * 从登录凭证中解析用户 ID，若凭证无效则返回 0
     *
     * @param token 登录凭证
     */
    int getUserIdByToken(String token);
}
