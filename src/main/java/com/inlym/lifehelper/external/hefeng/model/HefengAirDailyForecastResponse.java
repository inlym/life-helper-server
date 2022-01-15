package com.inlym.lifehelper.external.hefeng.model;

import lombok.Data;

/**
 * 空气质量预报 HTTP 请求响应数据
 *
 * @author inlym
 * @see <a href="https://dev.qweather.com/docs/api/air/air-daily-forecast/">空气质量预报</a>
 * @since 2022-01-15 23:17
 **/
public class HefengAirDailyForecastResponse {
    /** API 状态码 */
    private String code;

    /** 当前 API 的最近更新时间 */
    private String updateTime;

    private AirDailyForecast[] daily;

    @Data
    public static class AirDailyForecast {
        /** 预报日期 */
        private String fxDate;

        /** 空气质量指数 */
        private String aqi;

        /** 空气质量指数等级 */
        private String level;

        /** 空气质量指数级别 */
        private String category;

        /** 空气质量的主要污染物，空气质量为优时，返回值为 NA */
        private String primary;
    }
}
