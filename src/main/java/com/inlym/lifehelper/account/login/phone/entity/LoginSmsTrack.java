package com.inlym.lifehelper.account.login.phone.entity;

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
 * 登录短信验证码生命周期追踪表
 *
 * <h2>主要用途
 * <p>记录使用手机号（短信验证码）的方式进行登录的过程中，各个时间节点情况。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/13
 * @since 2.3.0
 **/
@Table("login_sms_track")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginSmsTrack {
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

    // ============================ 业务字段 ============================

    /** 手机号 */
    private String phone;

    /** 短信验证码 */
    private String code;

    /** 校验码 */
    private String checkCode;

    /** 客户端 IP 地址 */
    private String ip;

    /** 短信发送时间 */
    private LocalDateTime sendTime;

    /** 计划失效时间 */
    private LocalDateTime plannedDisableTime;

    /** 响应数据返回的业务码 */
    private String bizCode;

    /** 用户首次尝试进行登录验证时间 */
    private LocalDateTime firstAttemptTime;

    /** 用户尝试进行登录验证的次数 */
    private Integer attemptCounter;

    /** 匹配成功时间 */
    private LocalDateTime succeedTime;

    /** 登录成功后记录关联的用户手机号账户表 ID */
    private Long userAccountPhoneId;
}
