package com.inlym.lifehelper.weather.weatherdata.pojo;

import lombok.Data;

/**
 * @author inlym
 * @date 2022-02-19
 **/
@Data
public class MinutelyRain {
    // ============================== 数据处理后新增的字段 ==============================

    /**
     * 是否有雨
     *
     * <h2>字段规则
     * <p>遍历列表降水量值，只要存在不为零的项，该值即为 true
     */
    private Boolean hasRain = false;

    /**
     * 降水类型: - rain -> 雨 - snow -> 雪
     */
    private String type;

    // ========================== 和风天气原有的字段但是做了数据处理 ===========================

    /** 当前 API 的最近更新时间，格式示例：`07:55` */
    private String updateTime;

    // ============================== 和风天气原有的字段 ===============================

    /** 分钟降水描述 */
    private String summary;

    private Minutely[] minutely;

    @Data
    public static class Minutely {
        /** 预报时间，只保留时和分，格式示例 `19:06` */
        private String time;

        /** 10分钟累计降水量，单位毫米 */
        private Float precip;
    }
}
