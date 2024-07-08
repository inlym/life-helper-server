package com.weutil.account.login.qrcode.constant;

/**
 * 扫码登录凭据状态
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/26
 * @since 2.2.0
 **/
public enum LoginTicketStatus {
    /**
     * 已创建
     *
     * <h2>状态说明
     * <p>被扫码端发起获取小程序码后，生成的凭据的默认状态。
     */
    CREATED,

    /**
     * 已扫码
     *
     * <h2>状态说明
     * <p>（1）扫码端（小程序）扫小程序码后，发起请求，改为该状态。
     * <p>（2）该状态仅用于被扫码端（Web）展示，不附带任何逻辑。
     */
    SCANNED,

    /**
     * 已确认
     *
     * <h2>状态说明
     * <p>用户在扫码端（小程序）操作「确认登录」后，变更为该状态。
     */
    CONFIRMED,

    /**
     * 已使用
     *
     * <h2>状态说明
     * <p>（1）「使用」指的是消耗该「扫码登录凭据」用于生成登录凭证。
     * <p>（2）目前，该状态实际上未用到，因为发生「使用」行为后，「扫码登录凭据」直接被物理销毁了。
     */
    CONSUMED,
}
