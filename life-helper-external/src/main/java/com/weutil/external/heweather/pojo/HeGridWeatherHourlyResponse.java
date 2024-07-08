package com.weutil.external.heweather.pojo;

import lombok.Data;

/**
 * 获取格点逐小时天气预报响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/19
 * @see <a href="https://dev.qweather.com/docs/api/grid-weather/grid-weather-hourly-forecast/">格点逐小时天气预报</a>
 * @since 1.5.0
 **/
@Data
public class HeGridWeatherHourlyResponse {
    /** API 状态码 */
    private String code;

    /** 当前 API 的最近更新时间 */
    private String updateTime;

    private GridHourly[] hourly;

    @Data
    public static class GridHourly {
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

        /** 云量，百分比数值。可能为空 */
        private String cloud;

        /** 露点温度。可能为空 */
        private String dew;
    }
}
