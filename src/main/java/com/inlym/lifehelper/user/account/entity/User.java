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
 *
 * <h2>说明
 * <p>当前数据表不存储账户关联关系（存于其他“用户账户表”），其他数据表通过关联信息指向对应用户 ID：
 * <p>1. {@code UserAccountWeChat} 微信账户关联表
 * <p>2. {@code UserAccountPhone} 手机号账户关联表（当前无）
 * <p>3. {@code UserAccountGithub} Github账户关联表（当前无）
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @since 2.3.0
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

    /** 昵称 */
    private String nickName;

    /** 头像路径 */
    private String avatarPath;

    /** 注册时间 */
    @Column(onInsertValue = "now()")
    private LocalDateTime registerTime;

    /** 最近一次登录时间 */
    @Column(onInsertValue = "now()")
    private LocalDateTime lastLoginTime;

    /** 登录次数 */
    @Column(onInsertValue = "0")
    private Long loginCounter;

    /** 创建时间 */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /** 更新时间 */
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;

    /** 乐观锁（修改次数） */
    @Column(version = true)
    private Long version;
}
