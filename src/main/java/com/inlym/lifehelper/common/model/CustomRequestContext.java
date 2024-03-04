package com.inlym.lifehelper.common.model;

import com.inlym.lifehelper.common.constant.ClientType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 自定义请求上下文
 *
 * <h2>主要用途
 * <p>将所有用到的请求域字段数据存储在当前对象中，以便后续使用。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/23
 * @since 1.7.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomRequestContext {
    /** 在请求域属性使用的名称 */
    public static final String NAME = "CUSTOM_REQUEST_CONTEXT";

    /**
     * 请求 ID（追踪 ID）
     *
     * <h2>说明
     * <p>生产环境中会由 API 网关在请求头中传入，在开发环境需模拟生成该值。
     */
    private String traceId;

    /** 请求时间 */
    private LocalDateTime requestTime;

    /** 请求方法 */
    private String method;

    /** 请求路径 */
    private String path;

    /** 请求参数 */
    private String querystring;

    /** 请求数据 */
    private String requestBody;

    /** 响应状态码 */
    private Integer status;

    /** 响应数据 */
    private String responseBody;

    /**
     * 客户端 IP 地址
     *
     * <h2>说明
     * <p>生产环境中会由 API 网关在请求头中传入，而不是通过连接直接获取。
     */
    private String clientIp;

    /**
     * 用户 ID
     *
     * <h2>说明
     * <p>务必在鉴权通过后再存入。
     */
    private Long userId;

    /**
     * 客户端类型
     *
     * <h2>说明
     * <p>目前就2个：小程序、Web
     */
    private ClientType clientType;

    /** 客户端版本号 */
    private String clientVersion;
}
