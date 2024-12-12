package com.weutil.common.model;

/**
 * 自定义请求域属性
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/12
 * @since 3.0.0
 **/
public abstract class CustomRequestAttribute {
    /** 追踪 ID */
    public static final String TRACE_ID = "TRACE_ID";

    /** 访问令牌 */
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    /** 客户端 IP 地址 */
    public static final String CLIENT_IP = "CLIENT_IP";

    /** 用户 ID */
    public static final String USER_ID = "USER_ID";

    /** 客户端类型 */
    public static final String CLIENT_TYPE = "CLIENT_TYPE";

    /** 客户端 ID */
    public static final String CLIENT_ID = "CLIENT_ID";

    /** 客户端版本 */
    public static final String CLIENT_VERSION = "CLIENT_VERSION";
}
