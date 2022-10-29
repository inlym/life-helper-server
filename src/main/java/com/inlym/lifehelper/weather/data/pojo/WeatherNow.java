package com.inlym.lifehelper.weather.data.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 实时天气数据
 * <p>
 * [数据来源] 1. 复用了大部分从和风天气响应获取的数据。 2. 对部分原有数据做了数据处理后返回。
 *
 * @author inlym
 * @date 2022-02-19
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherNow {
    // =============================  数据处理后新增的字段  =============================

    /** 天气图标的 URL 地址 */
    private String iconUrl;

    /** 自行归纳的天气类型 */
    private String type;

    /** 风相关元素 */
    private Wind wind;

    // =============================  和风天气原有的字段  ==============================

    /** 当前 API 的最近更新时间 */
    private LocalDateTime updateTime;

    /** 温度，默认单位：摄氏度 */
    private String temp;

    /** 天气状况的文字描述，包括阴晴雨雪等天气状态的描述 */
    private String text;

    /** 相对湿度，百分比数值 */
    private String humidity;

    /** 大气压强，默认单位：百帕 */
    private String pressure;

    /** 能见度，默认单位：公里 */
    private String vis;
}
