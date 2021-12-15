package com.inlym.lifehelper.common;

/**
 * 使用到的请求属性值
 * <p>
 * 在使用 `request.getAttribute` 和 `request.setAttribute` 方法时，不要直接操作字符串，
 * 应该直接当做常量存于此处。
 */
public interface RequestAttributeName {
    /**
     * 唯一请求 ID
     */
    String REQUEST_ID = "REQUEST_ID";

    /**
     * 用户 ID
     */
    String USER_ID = "USER_ID";

    /**
     * 客户端的 IP 地址
     */
    String CLIENT_IP = "CLIENT_IP";
}
