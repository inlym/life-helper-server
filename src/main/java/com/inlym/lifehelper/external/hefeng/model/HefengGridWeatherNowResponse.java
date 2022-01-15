package com.inlym.lifehelper.external.hefeng.model;

import lombok.Data;

/**
 * 格点实时天气 HTTP 请求响应数据
 *
 * @author inlym
 * @see <a href="https://dev.qweather.com/docs/api/grid-weather/grid-weather-now/">格点实时天气</a>
 * @since 2022-01-15 23:58
 **/
@Data
public class HefengGridWeatherNowResponse {
    /** API 状态码 */
    private String code;

    /** 当前 API 的最近更新时间 */
    private String updateTime;

    private GridWeatherNow now;

    @Data
    public static class GridWeatherNow {
        /** 数据观测时间 */
        private String obsTime;

        /** 温度，默认单位：摄氏度 */
        private String temp;

        /** 天气状况和图标的代码 */
        private String icon;

        /** 天气状况的文字描述，包括阴晴雨雪等天气状态的描述 */
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

        /** 大气压强，默认单位：百帕 */
        private String pressure;
    }
}
