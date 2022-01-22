package com.inlym.lifehelper.external.hefeng.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分钟级降水
 *
 * @author inlym
 * @since 2022-01-22 18:54
 **/
@Data
@NoArgsConstructor
public class MinutelyRain {
    /** 当前 API 的最近更新时间，不是原接口返回的时间节点，而是该时间与当前时间的时间差，单位：分钟 */
    private String updateMinutesDiff;

    /** 分钟降水描述 */
    private String summary;

    private MinutelyRainItem[] list;

    @Data
    public static class MinutelyRainItem {
        /** 预报时间，只保留时和分，格式示例 `19:06` */
        private String time;

        /** 10分钟累计降水量，单位毫米 */
        private String precipitation;

        /**
         * 降水类型:
         * - rain -> 雨
         * - snow -> 雪
         */
        private String type;
    }
}
