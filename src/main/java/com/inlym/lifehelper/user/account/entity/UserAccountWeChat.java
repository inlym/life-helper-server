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
 * 用户微信账户表
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @since 2.3.0
 **/
@Table("user_account_wechat")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountWeChat {
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

    /** 小程序开发者 ID */
    private String appId;

    /** 小程序的用户唯一标识 */
    private String openId;

    /** 用户在微信开放平台的唯一标识符 */
    private String unionId;

    /** 对应的用户 ID */
    private Long userId;

    /** 当前小程序登录次数 */
    @Column(onInsertValue = "0")
    private Long counter;

    /** 最近一次使用（指通过当前行记录用于登录）时间 */
    @Column(onInsertValue = "now()")
    private LocalDateTime lastTime;
}
