package com.inlym.lifehelper.account.login.phone.entity;

import com.inlym.lifehelper.common.base.aliyun.sms.constant.SendingStatus;
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
 * <h2>追踪起点
 * <p>用户在前台点击“获取验证码”（不管短信是否正确发出）。
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

    // 字段说明：因为是“追踪表”，因此此处无“逻辑删除标志”字段

    // ============================ 业务字段 ============================

    // ---------- 短信发出前确认的字段 ----------

    /** 手机号 */
    private String phone;

    /** 短信验证码 */
    private String code;

    /**
     * 校验码
     *
     * <h3>说明
     * <p>32位随机字符串，使用唯一约束。
     */
    private String checkTicket;

    /** 客户端 IP 地址 */
    private String ip;

    /** 短信发送时间（发送前） */
    private LocalDateTime preSendTime;

    /** 发送状态 */
    private SendingStatus sendingStatus;

    // ---------- 短信发出后，从发送结果反馈获得的的字段 ----------
    // 文档地址：https://next.api.aliyun.com/document/Dysmsapi/2017-05-25/SendSms

    /** 请求状态码 */
    private String resCode;

    /** 状态码的描述 */
    private String resMessage;

    /** 发送回执 ID */
    private String resBizId;

    /** 请求 ID */
    private String requestId;

    // ---------- 短信发出后，得到相应后处理的字段 ----------

    /** 收到响应的时间 */
    private LocalDateTime postSendTime;

    /** 用户首次尝试进行登录验证时间 */
    private LocalDateTime firstAttemptTime;

    /** 用户最后一次尝试进行登录验证时间 */
    private LocalDateTime lastAttemptTime;

    /** 用户尝试进行登录验证的次数，默认值：0 */
    private Integer attemptCounter;

    /** 匹配成功时间 */
    private LocalDateTime succeedTime;

    // ---------- 关联 ID ----------

    /** 登录成功后记录关联的手机号验证码登录日志表 ID */
    private Long phoneSmsLoginLogId;

    /** 登录成功后记录关联的用户手机号账户表 ID */
    private Long userAccountPhoneId;
}
