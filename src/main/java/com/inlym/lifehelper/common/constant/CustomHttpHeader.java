package com.inlym.lifehelper.common.constant;

/**
 * 自定义的 HTTP 请求头
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-19
 * @since 1.0.0
 */
public final class CustomHttpHeader {
    /** 请求 ID，用作全链路追踪 ID */
    public static final String REQUEST_ID = "X-Ca-Request-Id";

    /** 客户端 IP 地址，由 API 网关层加入到请求头中 */
    public static final String CLIENT_IP = "X-Client-Ip";

    /** JWT 字符串 */
    public static final String JWT_TOKEN = "X-Auth-Jwt";
}
