package com.weutil.common.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestLog {
    // ============================ 通用字段 ============================

    /** 主键 ID */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    // 字段说明：因为是“日志表”，因此此处无“逻辑删除标志”字段

    // ============================ 业务字段 ============================

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

    /**
     * 用户 ID
     *
     * <h2>说明
     * <p>务必在鉴权通过后再存入。
     */
    private Long userId;
}
