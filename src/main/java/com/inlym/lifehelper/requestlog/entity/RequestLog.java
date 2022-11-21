package com.inlym.lifehelper.requestlog.entity;

import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.Tag;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.TimeseriesTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 请求日志
 *
 * <h2>数据定义
 * <p>来源于请求信息，并对部分信息进行的拓展
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/21
 * @since 1.7.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TimeseriesTable(measurementName = "api", dataSource = "miniprogram")
public class RequestLog {
    /** 用户 ID */
    @Tag
    private Integer userId;

    /** 当前日期 */
    @Tag
    private LocalDate date;

    /** 请求 ID */
    private String requestId;

    /** 请求方法 */
    private String method;

    /** 请求路径 */
    private String path;

    /** 请求参数 */
    private String querystring;

    /** IP 地址 */
    private String ip;

    // ========================== 通过 IP 地址转化的省、市、区信息 ==========================

    /** 省 */
    private String province;

    /** 市 */
    private String city;

    /** 区 */
    private String district;
}
