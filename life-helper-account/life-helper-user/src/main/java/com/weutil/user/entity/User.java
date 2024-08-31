package com.weutil.user.entity;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    // ============================ 通用字段 ============================

    /** 主键 ID，即业务含义上的“用户 ID” */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    // 字段备注：无逻辑删除字段

    // ============================ 业务字段 ============================

    /** 昵称 */
    private String nickName;

    /** 头像路径 */
    private String avatarPath;

    /** 注册时间 */
    @Column(onInsertValue = "now()")
    private LocalDateTime registerTime;
}
