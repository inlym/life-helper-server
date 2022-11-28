package com.inlym.lifehelper.user.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 用户账户实体
 *
 * <h2>主要用途
 * <p>仅包含用户账户权限相关字段。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/26
 * @since 1.7.0
 **/
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /** 用户 ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 微信小程序用户唯一标识 */
    private String openid;

    /**
     * 账户 ID
     *
     * <h2>主要用途
     * <p>给用户展示的 ID。
     */
    private Integer accountId;

    /** 注册时间 */
    private LocalDateTime registerTime;

    // 备注（2022.11.29）
    // 线上大概还有600多用户设置了昵称头像，直接清空并不合适，因此还是做一次数据迁移到新的数据表
    // 之后再删除这个字段。

    /** 用户昵称 */
    private String nickName;

    /** 用户头像图片路径 */
    private String avatar;
}
