package com.weutil.account.entity;

import com.mybatisflex.annotation.Table;
import com.weutil.account.model.LoginChannel;
import com.weutil.account.model.LoginType;
import com.weutil.common.entity.BaseUserRelatedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 登录日志
 *
 * <h2>说明
 * <p>原本每种登录方式分别一张表，每张表有80%左右的字段重合，为方便统计和使用，合并到一张表上。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/8/28
 * @since 3.0.0
 **/
@Table("login_log")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LoginLog extends BaseUserRelatedEntity {

    // ---------- 各种登录方式通用项 ----------

    /** 登录方式 */
    private LoginType type;

    /** 登录渠道 */
    private LoginChannel channel;

    /** 发放的鉴权令牌 */
    private String token;

    /** 客户端 IP 地址 */
    private String ip;

    /** 登录时间 */
    private LocalDateTime loginTime;

    // ---------- 各种登录方式差异项 ----------

    // --- 通过手机号系列方式登录 ---

    /** 关联的用户手机号账户表 ID */
    private Long phoneAccountId;
}
