package com.inlym.lifehelper.common.constant;

/**
 * 自定义的 HTTP 请求头
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-19
 * @since 1.0.0
 */
public abstract class CustomHttpHeader {
    /** 请求 ID，用作全链路追踪 ID */
    public static final String REQUEST_ID = "x-lifehelper-request-id";

    /** JWT 登录令牌 */
    public static final String JWT_TOKEN = "x-lifehelper-auth-jwt";

    /** 简易登录令牌 */
    public static final String SIMPLE_TOKEN = "x-lifehelper-auth-st";

    /** 超级登录令牌 */
    public static final String SUPER_TOKEN = "x-lifehelper-auth-super-token";

    /** 客户端信息 */
    public static final String CLIENT_INFO = "x-lifehelper-client-info";
}
