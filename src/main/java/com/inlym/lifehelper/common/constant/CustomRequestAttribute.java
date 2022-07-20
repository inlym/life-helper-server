package com.inlym.lifehelper.common.constant;

/**
 * 自定义请求域属性值
 *
 * <h2>注意事项
 * <li>在使用 {@code request.getAttribute} 和 {@code request.setAttribute} 方法时，不要直接操作字符串，应该直接当做常量存于此处。
 *
 * <pre class="code">
 *     request.setAttribute(CustomRequestAttribute.CLIENT_IP, clientIp);
 * </pre>
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-19
 * @since 1.0.0
 */
public abstract class CustomRequestAttribute {
    /** 请求 ID */
    public static final String REQUEST_ID = "REQUEST_ID";

    /** 用户 ID */
    public static final String USER_ID = "USER_ID";

    /** 客户端 IP 地址 */
    public static final String CLIENT_IP = "CLIENT_IP";
}
