package com.inlym.lifehelper.external.hefeng.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实时天气数据
 * <p>
 * [与和风天气 HTTP 请求的响应数据比较]
 * 1. 基本上保留了原有的有效字段（字段名保持不变）。
 * 2. 移除了部分用不到的字段。
 * 3. 新增了处理后的字段。
 *
 * @author inlym
 * @since 2022-01-21 21:36
 **/
@Data
@NoArgsConstructor
public class WeatherNow {
    // ====================  新增的字段  ====================

    /** 当前 API 的最近更新时间，不是原接口返回的时间节点，而是该时间与当前时间的时间差，单位：分钟 */
    private String updateMinutesDiff;

    /** 图标的 URL 地址 */
    private String iconUrl;

    // ====================  原有的字段  ====================

    /** 温度，默认单位：摄氏度 */
    private String temp;

    /** 体感温度，默认单位：摄氏度 */
    private String feelsLike;

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

    /** 能见度，默认单位：公里 */
    private String vis;
}
