package com.inlym.lifehelper.account.user.entity;

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
 * 用户手机号账户表
 *
 * <h2>说明
 * <p>通过手机号关联到账户。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/12
 * @since 2.3.0
 **/
@Table("user_account_phone")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountPhone {
    // ============================ 通用字段 ============================

    /** 主键 ID */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /** 创建时间 */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /** 更新时间 */
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;

    /** 乐观锁（修改次数） */
    @Column(version = true)
    private Long version;

    /** 删除时间（逻辑删除标志） */
    @Column(isLogicDelete = true)
    private LocalDateTime deleteTime;

    // ============================ 业务字段 ============================

    // ---------- 账户关联表通用项 ----------

    /** 对应的用户 ID */
    private Long userId;

    /** 登录次数 */
    @Column(onInsertValue = "0")
    private Long loginCounter;

    /** 最近一次登录时间 */
    @Column(onInsertValue = "now()")
    private LocalDateTime lastLoginTime;

    /** 最近一次登录的 IP 地址 */
    private String lastLoginIp;

    // ---------- 账户关联表差异项 ----------

    /** 手机号（仅支持国内手机号） */
    private String phone;

    /** 使用 HmacSHA256 算法哈希化后的密码 */
    private String hashedPassword;
}
