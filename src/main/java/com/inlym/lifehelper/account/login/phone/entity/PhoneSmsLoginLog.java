package com.inlym.lifehelper.account.login.phone.entity;

import com.inlym.lifehelper.account.user.model.LoginLog;
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
 * 手机号（短信验证码）登录记录日志
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
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /** 更新时间 */
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;

    // ============================ 业务字段 ============================

    /** 手机号 */
    private String phone;

    /** 短信验证码 */
    private String code;

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
}
