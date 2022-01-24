package com.inlym.lifehelper.external.hefeng.model;

import lombok.Data;

/**
 * 空气质量预报
 *
 * @author inlym
 * @since 2022-01-24 23:54
 **/
@Data
public class WeatherAirDailyForecast {
    /** 预报日期 */
    private String date;

    /** 空气质量指数 */
    private String aqi;

    /** 空气质量指数等级 */
    private String level;

    /** 空气质量指数级别 */
    private String category;

    /** 空气质量的主要污染物，空气质量为优时，返回值为 NA */
    private String primary;
}
