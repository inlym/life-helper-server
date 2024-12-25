package com.weutil.common.entity;

import com.mybatisflex.annotation.Table;
import com.weutil.common.model.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 请求日志
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/12
 * @since 3.0.0
 **/
@Table("request_log")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RequestLog extends BaseUserRelatedEntity {

    // ---------- 原始数据字段 ----------

    /** 请求方法 */
    private String method;

    /** 请求路径 */
    private String path;

    /** 请求参数 */
    private String querystring;

    /** 响应状态码 */
    private Integer status;

    /** 请求数据 */
    private String requestBody;

    // ---------- 自定义数据处理字段 ----------

    /** 请求开始时间 */
    private LocalDateTime startTime;

    /** 请求结束时间 */
    private LocalDateTime endTime;

    /** 请求时长（单位：毫秒） */
    private Long duration;

    /**
     * 请求 ID（追踪 ID）
     *
     * <h2>说明
     * <p>生产环境中会由 API 网关在请求头中传入，在开发环境需模拟生成该值。
     */
    private String traceId;

    /**
     * 客户端 IP 地址
     *
     * <h2>说明
     * <p>生产环境中会由 API 网关在请求头中传入，而不是通过连接直接获取。
     */
    private String clientIp;

    /** 访问凭证 */
    private String accessToken;

    /** 客户端类型 */
    private ClientType clientType;

    /** 客户端 ID */
    private String clientId;

    /** 客户端版本号 */
    private String clientVersion;
}
