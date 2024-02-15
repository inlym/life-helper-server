package com.inlym.lifehelper.user.account.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户账户实体
 * <p>
 * <h2>主要用途
 * <p>仅包含用户账户权限相关字段。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/26, 2024/02/15
 * @since 1.7.0
 **/
@Table("user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /** 用户 ID */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 用户在微信开放平台的唯一标识符
     *
     * <h2>说明
     * <p>1. 微信文档地址：<a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-login/code2Session.html">小程序登录</a>
     * <p>2. 使用 unionid 而非 openid 的原因是：项目会包含多个小程序，需要在多个小程序上使用一套账户体系。
     */
    private String unionId;

    /** 注册时间 */
    @Column(onInsertValue = "now()")
    private LocalDateTime registerTime;

    /** 创建时间（该字段值由数据库自行维护，请勿手动赋值） */
    private LocalDateTime createTime;

    /** 更新时间（该字段值由数据库自行维护，请勿手动赋值） */
    private LocalDateTime updateTime;
}
