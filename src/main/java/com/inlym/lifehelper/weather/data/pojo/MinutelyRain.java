package com.inlym.lifehelper.weather.data.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
     * 降水类型: - rain -> 雨 / snow -> 雪
     */
    private String type;

    // ============================== 和风天气原有的字段 ===============================

    /** 当前 API 的最近更新时间 */
    private LocalDateTime updateTime;

    /** 分钟降水描述 */
    private String summary;

    private List<Minutely> minutely;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Minutely {
        /** 预报时间 */
        private LocalDateTime time;

        /** 10分钟累计降水量，单位毫米 */
        private Float precip;
    }
}
