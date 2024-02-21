package com.inlym.lifehelper.common.constant;

/**
 * 客户端类型
 *
 * <h2>说明
 * <p>指发起请求的客户端。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/28
 * @since 1.7.0
 **/
public enum ClientType {
    /** 未知 */
    UNKNOWN,

    /** 微信小程序 */
    MINI_PROGRAM,

    /** Web 网页 */
    WEB;

    ClientType() {}
}
