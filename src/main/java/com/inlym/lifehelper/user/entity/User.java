package com.inlym.lifehelper.user.entity;

import lombok.Data;

/**
 * 用户账户实体
 * <p>
 * 表名：`user`
 */
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
