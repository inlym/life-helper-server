package com.weutil.account.login.phone.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.weutil.account.login.common.model.LoginLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 手机号（短信验证码）登录日志表
 *
 * <h2>主要用途
 * <p>记录使用手机号（短信验证码）的方式进行登录的行为。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/12
 * @since 2.3.0
 **/
@Table("login_phone_sms_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneSmsLoginLog implements LoginLog {
    // ============================ 通用字段 ============================

    /** 主键 ID */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    // 字段说明：因为是“日志表”，因此此处无“逻辑删除标志”字段

    // ============================ 业务字段 ============================

    // ---------- 手机号登录日志通用项 ----------

    /** 手机号 */
    private String phone;

    /** 关联的用户手机号账户表 ID */
    private Long userAccountPhoneId;

    /** 对应的用户 ID */
    private Long userId;

    /** 发放的鉴权令牌 */
    private String token;

    /** 客户端 IP 地址 */
    private String ip;

    /** 登录时间 */
    private LocalDateTime loginTime;

    // ---------- 手机号登录日志差异项 ----------

    /** 短信验证码 */
    private String code;
}
