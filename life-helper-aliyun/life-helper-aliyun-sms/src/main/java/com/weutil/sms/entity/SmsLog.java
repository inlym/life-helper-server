package com.weutil.sms.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 短信发送记录
 *
 * <h2>说明
 * <p>1. 仅记录短信发送，不处理其他的业务逻辑。
 * <p>2. 目前只包含“验证码”短信。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/11/4
 * @since 3.0.0
 **/
@Table("sms_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsLog {
    // ============================ 通用字段 ============================

    /** 主键 ID */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    // 字段说明：因为是“日志表”，不存在删除操作，因此此处无“逻辑删除标志”字段

    // ============================ 业务字段 ============================

    /** 手机号 */
    private String phone;

    /** 短信验证码 */
    private String code;

    /** 客户端 IP 地址 */
    private String ip;

    /** 短信发送时间（发送前） */
    private LocalDateTime preSendTime;

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
}
