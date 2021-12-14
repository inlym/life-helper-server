package com.inlym.lifehelper.user;

public interface UserService {
    /**
     * 通过 openid 获取用户 ID（用户不存在时将自动创建用户）
     *
     * @param openid 微信小程序用户唯一标识
     */
    int getUserIdByOpenid(String openid);
}
