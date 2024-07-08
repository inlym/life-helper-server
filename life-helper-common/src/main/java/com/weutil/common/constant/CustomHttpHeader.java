package com.weutil.common.constant;

/**
 * 自定义的 HTTP 请求头
 * <p>
 * <h2>说明
 * <p>为避免冲突，所有请求头均以 `x-lifehelper-` 开头。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/1/19
 * @since 1.0.0
 */
public abstract class CustomHttpHeader {

    /** 请求 ID，用作全链路追踪 ID */
    public static final String REQUEST_ID = "x-lifehelper-request-id";

    /** 登录令牌 */
    public static final String AUTH_TOKEN = "x-lifehelper-auth-token";

    /** 客户端 IP 地址 */
    public static final String CLIENT_IP = "x-forwarded-for";

    /**
     * 客户端类型
     *
     * <h2>说明
     * <p>1. `miniprogram` -> 小程序
     * <p>2. `web` -> 网页
     */
    public static final String CLIENT_TYPE = "x-lifehelper-client-type";

    /**
     * 客户端信息
     *
     * <h2>说明
     * <p>1. 组合多项键值对，使用 `; ` 分割。
     * <p>2. 文本格式示例：`key1=value1; key2=value2; key3=value3`
     */
    public static final String CLIENT_INFO = "x-lifehelper-client-info";
}
