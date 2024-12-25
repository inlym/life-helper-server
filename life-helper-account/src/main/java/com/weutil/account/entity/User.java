package com.weutil.account.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.weutil.account.model.Gender;
import com.weutil.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 用户账户实体
 *
 * <h2>说明
 * <p>当前数据表不存储账户关联关系（存于其他“用户账户表”），其他数据表通过关联信息指向对应用户 ID：
 * <p>1. {@code WeChatAccount} 微信账户关联表
 * <p>2. {@code PhoneAccount} 手机号账户关联表
 * <p>3. {@code GithubAccount} Github账户关联表（当前无）
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
@Table("user")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    /** 昵称 */
    private String nickName;

    /** 头像路径 */
    private String avatarPath;

    /**
     * 账户 ID
     *
     * <h3>字段说明
     * <p>（1）用于展示用途，客户端展示名称为 UID
     * <p>（2）该字段添加“唯一索引”
     */
    private Long accountId;

    /** 性别 */
    private Gender gender;

    /** 注册时间 */
    @Column(onInsertValue = "now()")
    private LocalDateTime registerTime;
}
