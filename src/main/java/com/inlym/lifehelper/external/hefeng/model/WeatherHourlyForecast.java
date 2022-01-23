package com.inlym.lifehelper.external.hefeng.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 逐小时天气预报
 *
 * @author inlym
 * @since 2022-01-23 23:15
 **/
@Data
@NoArgsConstructor
public class WeatherHourlyForecast {
    // ====================  新增的字段  ====================

    /** 预报时间 */
    private String time;

    /** 天气状况和图标 URL 地址 */
    private String iconUrl;

    // ====================  原有的字段  ====================

    /** 温度，默认单位：摄氏度 */
    private String temp;

    /** 天气状况的文字描述 */
    private String text;

    /** 风向360角度 */
    private String wind360;

    /** 风向 */
    private String windDir;

    /** 风力等级 */
    private String windScale;

    /** 风速，公里/小时 */
    private String windSpeed;

    /** 相对湿度，百分比数值 */
    private String humidity;

    /** 当前小时累计降水量，默认单位：毫米 */
    private String precip;

    /** 逐小时预报降水概率，百分比数值，可能为空 */
    private String pop;

    /** 大气压强，默认单位：百帕 */
    private String pressure;
}
