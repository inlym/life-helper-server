package com.inlym.lifehelper.extern.heweather.pojo;

import lombok.Data;

/**
 * 和风天气分钟级降水响应数据
 *
 * @author inlym
 * @date 2022-02-17
 * @see <a href="https://dev.qweather.com/docs/api/grid-weather/minutely/">分钟级降水</a>
 **/
@Data
public class HeMinutelyResponse {
    /** API 状态码 */
    private String code;

    /** 当前 API 的最近更新时间 */
    private String updateTime;

    /** 分钟降水描述 */
    private String summary;

    private Minutely[] minutely;

    @Data
    public static class Minutely {
        /** 预报时间 */
        private String fxTime;

        /** 10分钟累计降水量，单位毫米 */
        private String precip;

        /**
         * 降水类型: - rain -> 雨 - snow -> 雪
         */
        private String type;
    }
}
