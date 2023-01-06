package com.inlym.lifehelper.login.scan.constant;

/**
 * 扫码登录凭据状态
 *
 * <h2>状态说明
 * <li>
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/1/5
 * @since 1.9.0
 **/
public enum ScanLoginTicketStatus {
    /** 已创建 */
    CREATED,

    /** 已扫码 */
    SCANNED,

    /** 已确认 */
    CONFIRMED,

    /** 已使用（用于生成登录凭证） */
    CONSUMED
}
