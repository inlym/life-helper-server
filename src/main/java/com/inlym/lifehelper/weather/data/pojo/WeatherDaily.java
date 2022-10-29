package com.inlym.lifehelper.weather.data.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 逐天天气预报中单天的数据详情
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-19
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDaily {
    // =============================  数据处理后新增的字段  =============================

    /** 预报日期，格式示例：2022-04-29 */
    private LocalDate date;

    private HalfDay day;

    private HalfDay night;

    /** 月相图标 URL 地址 */
    private String moonPhaseIconUrl;

    /** 天气总结，示例：晴转多云 */
    private String text;

    /** 日出日落 */
    private Star sun;

    /** 月升月落 */
    private Star moon;

    /** 空气质量预报 */
    private AirDaily air;

    // =============================  和风天气原有的字段  ==============================

    /** 月相名称 */
    private String moonPhase;

    /** 预报当天总降水量，默认单位：毫米 */
    private String precip;

    /** 紫外线强度指数 */
    private String uvIndex;

    /** 相对湿度，百分比数值 */
    private String humidity;

    /** 大气压强，默认单位：百帕 */
    private String pressure;

    /** 能见度，默认单位：公里 */
    private String vis;

    /** 将半天作为一个基础天气单元 */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HalfDay {
        /** 温度，默认单位：摄氏度 */
        private String temp;

        /** 天气状况的文字描述，包括阴晴雨雪等天气状态的描述 */
        private String text;

        /** 天气图标的 URL 地址 */
        private String iconUrl;

        private Wind wind;
    }
}
