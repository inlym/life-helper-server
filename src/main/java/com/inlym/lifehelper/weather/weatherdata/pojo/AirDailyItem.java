package com.inlym.lifehelper.weather.weatherdata.pojo;

import lombok.Data;

/**
 * 空气质量逐天预报中的单天详情
 *
 * @author inlym
 * @date 2022-02-19
 **/
@Data
public class AirDailyItem {
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
