package com.inlym.lifehelper.user.pojo;

import lombok.Data;

/**
 * 用户个人信息业务对象
 *
 * <h2>使用说明
 * <p>返回给客户端使用的个人资料，均使用当前业务对象进行封装，不要直接返回 {@link com.inlym.lifehelper.user.entity.User} 实体。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-13
 * @since 1.0.0
 **/
@Data
public class UserInfoBO {
    /** 用户昵称 */
    private String nickName;

    /** 用户头像图片的 URL */
    private String avatarUrl;

    /** 用户信息是否为空 */
    private Boolean empty;

    /**
     * 注册天数
     *
     * <h2>说明
     * <p>注册当天天数记为 1，实际含义是注册第 n 天
     *
     * @since 1.1.1
     */
    private Long registeredDays;
}
