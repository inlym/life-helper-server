package com.weutil.external.heweather.pojo;

import lombok.Data;

/**
 * 获取格点逐日天气预报响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/19
 * @see <a href="https://dev.qweather.com/docs/api/grid-weather/grid-weather-daily-forecast/">格点逐日天气预报</a>
 * @since 1.5.0
 **/
@Data
public class HeGridWeatherDailyResponse {
    /** API 状态码 */
    private String code;

    /** 当前 API 的最近更新时间 */
    private String updateTime;

    private GridDaily[] daily;

    @Data
    public static class GridDaily {
        /** 预报日期 */
        private String fxDate;

        /** 预报当天最高温度 */
        private String tempMax;

        /** 预报当天最低温度 */
        private String tempMin;

        /** 预报白天天气状况的图标代码 */
        private String iconDay;

        /** 预报白天天气状况文字描述 */
        private String textDay;

        /** 预报夜间天气状况的图标代码 */
        private String iconNight;

        /** 预报晚间天气状况文字描述 */
        private String textNight;

        /** 预报白天风向 360 角度 */
        private String wind360Day;

        /** 预报白天风向 */
        private String windDirDay;

        /** 预报白天风力等级 */
        private String windScaleDay;

        /** 预报白天风速，公里/小时 */
        private String windSpeedDay;

        /** 预报夜间风向 360 角度 */
        private String wind360Night;

        /** 预报夜间当天风向 */
        private String windDirNight;

        /** 预报夜间风力等级 */
        private String windScaleNight;

        /** 预报夜间风速，公里/小时 */
        private String windSpeedNight;

        /** 预报当天总降水量，默认单位：毫米 */
        private String precip;

        /** 相对湿度，百分比数值 */
        private String humidity;

        /** 大气压强，默认单位：百帕 */
        private String pressure;
    }
}
