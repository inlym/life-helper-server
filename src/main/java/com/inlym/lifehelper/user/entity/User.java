package com.inlym.lifehelper.user.entity;

import lombok.Data;

@Data
public class User {
    /**
     * 用户 ID
     */
    private Integer id;

    /**
     * 微信小程序用户唯一标识
     */
    private String openid;

    /**
     * 绑定的账户手机号
     */
    private String phone;
}
