package com.inlym.lifehelper.user.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 用户实体
 *
 * <h2>说明
 *
 * <li>表名：{@code user}
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-22
 * @since 1.0.0
 */
@Data
@Builder
public class User {
    /** 用户 ID */
    private Integer id;

    /** 微信小程序用户唯一标识 */
    private String openid;

    /** 绑定的账户手机号 */
    private String phone;

    /** 用户昵称 */
    private String nickName;

    /**
     * 用户头像图片路径
     *
     * <h2>说明
     *
     * <p>从微信侧获取的头像 URL，会将图片内容转储到我方 OSS 中，最终存储的是在 OSS 中的路径。
     */
    private String avatar;

    /** 注册时间 */
    private Date registerTime;
}
