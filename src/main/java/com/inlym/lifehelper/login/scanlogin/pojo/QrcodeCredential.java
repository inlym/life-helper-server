package com.inlym.lifehelper.login.scanlogin.pojo;

import lombok.Data;

/**
 * 二维码鉴权凭证
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/4/12
 * @since 1.1.0
 **/
@Data
public class QrcodeCredential {
    /** 凭证编号，一般是去掉短横线的 UUID */
    private String ticket;

    /** 状态 */
    private CredentialStatus status = CredentialStatus.CREATED;

    /** 创建时间（时间戳） */
    private Long createTime;

    /** 扫码时间（时间戳） */
    private Long scanTime;

    /** 确认时间（时间戳） */
    private Long confirmTime;

    /** 使用时间（时间戳） */
    private Long consumeTime;

    /** 扫码操作者用户 ID */
    private Integer userId;

    /** 凭证状态 */
    public enum CredentialStatus {
        /** 已创建 */
        CREATED,
        /** 已扫码但未确认 */
        SCANNED,
        /** 已扫码确认 */
        CONFIRMED,
        /** 已使用（用于生成登录凭证） */
        CONSUMED
    }
}
