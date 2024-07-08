package com.weutil.external.heweather.pojo;

import lombok.Data;

/**
 * 和风天气逐小时天气预报响应数据
 *
 * @author inlym
 * @date 2022-02-17
 **/
@Data
public class HeWeatherHourlyResponse {
    /** API 状态码 */
    private String code;

    /** 当前 API 的最近更新时间 */
    private String updateTime;

    private Hourly[] hourly;

    @Data
    public static class Hourly {
        /** 预报时间 */
        private String fxTime;

        /** 温度，默认单位：摄氏度 */
        private String temp;

        /** 天气状况和图标的代码 */
        private String icon;

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

        /** 云量，百分比数值。可能为空 */
        private String cloud;

        /** 露点温度。可能为空 */
        private String dew;
    }
}
