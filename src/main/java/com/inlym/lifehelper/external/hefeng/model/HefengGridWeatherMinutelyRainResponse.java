package com.inlym.lifehelper.external.hefeng.model;

import lombok.Data;

/**
 * 分钟级降水 HTTP 请求响应数据
 *
 * @author inlym
 * @see <a href="https://dev.qweather.com/docs/api/grid-weather/minutely/">分钟级降水</a>
 * @since 2022-01-15 23:00
 **/
@Data
public class HefengGridWeatherMinutelyRainResponse {
    /** API 状态码 */
    private String code;

    /** 当前 API 的最近更新时间 */
    private String updateTime;

    /** 分钟降水描述 */
    private String summary;

    @Data
    public static class GridMinutelyForecast {
        /** 预报时间 */
        private String fxTime;

        /** 10分钟累计降水量，单位毫米 */
        private String precip;

        /**
         * 降水类型:
         * - rain -> 雨
         * - snow -> 雪
         */
        private String type;
    }
}
