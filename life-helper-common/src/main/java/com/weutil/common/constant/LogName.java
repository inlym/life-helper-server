package com.weutil.common.constant;

/**
 * 日志变量名
 *
 * <h2>主要用途
 * <p>统一管理会被存储到 `MDC` 中应用于日志输出的内部变量名称。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/1/31
 * @since 1.9.0
 **/
public abstract class LogName {
    /** 请求方法 */
    public final static String METHOD = "method";

    /** 请求地址（路径 + 请求参数） */
    public final static String URL = "url";

    /** 客户端 IP 地址 */
    public final static String CLIENT_IP = "ip";

    /** 请求 ID */
    public final static String TRACE_ID = "rid";

    /** 用户 ID */
    public final static String USER_ID = "uid";
}
