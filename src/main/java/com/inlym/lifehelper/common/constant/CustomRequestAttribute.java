package com.inlym.lifehelper.common.constant;

/**
 * 自定义请求属性值
 * <p>
 * 在使用 `request.getAttribute` 和 `request.setAttribute` 方法时，不要直接操作字符串，
 * 应该直接当做常量存于此处。
 *
 * @author inlym
 * @since 2022-01-19 00:29
 */
public final class CustomRequestAttribute {
    /**
     * 唯一请求 ID
     */
    public static final String REQUEST_ID = "REQUEST_ID";

    /**
     * 用户 ID
     */
    public static final String USER_ID = "USER_ID";

    /**
     * 客户端的 IP 地址
     */
    public static final String CLIENT_IP = "CLIENT_IP";
}
