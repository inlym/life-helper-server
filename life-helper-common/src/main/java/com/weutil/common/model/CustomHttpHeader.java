package com.weutil.common.model;

/**
 * 自定义的 HTTP 请求头
 *
 * <h2>来源
 * <li>1. API 网关层传入
 * <li>2. 项目自定义
 *
 * <h2>命名规范
 * <li>为避免冲突，项目自定义的请求头均设定以 {@code x-weutil-} 开头
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @since 3.0.0
 **/
public abstract class CustomHttpHeader {
    /** 请求 ID，用作全链路追踪 ID */
    public static final String REQUEST_ID = "x-ca-request-id";

    /** 访问令牌 */
    public static final String ACCESS_TOKEN = "x-weutil-access-token";

    /** 客户端 IP 地址 */
    public static final String CLIENT_IP = "x-forwarded-for";

    /**
     * 客户端类型
     *
     * <h2>说明
     * <p>1. `miniprogram` -> 小程序
     * <p>2. `web` -> 网页
     */
    public static final String CLIENT_TYPE = "x-weutil-client-type";

    /**
     * 客户端信息
     *
     * <h2>说明
     * <p>1. 组合多项键值对，使用 `; ` 分割。
     * <p>2. 文本格式示例：`key1=value1; key2=value2; key3=value3`
     */
    public static final String CLIENT_INFO = "x-weutil-client-info";
}
