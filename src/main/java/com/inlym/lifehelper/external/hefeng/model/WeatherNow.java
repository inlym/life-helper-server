package com.inlym.lifehelper.external.hefeng.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实时天气数据
 * <p>
 * [为什么要有这个数据模型，而不是直接使用 HTTP 请求的响应数据模型]
 * （1）从数据字段来看，两者有超过一半是相同的，但是很多字段差异很大，建立2套模型更有灵活性。
 * （2）HTTP 请求的响应数据模型用于忠实地反映响应数据，不需要考虑业务层是如何使用该数据的，为了强行兼容会有更大的码放。
 * <p>
 * [说明]
 * 实时天气和格点实时天气共用这套数据模型。仅保留两者共有字段。
 *
 * @author inlym
 * @since 2022-01-21 21:36
 **/
@Data
@NoArgsConstructor
public class WeatherNow {
    /** 当前 API 的最近更新时间，不是原接口返回的时间节点，而是该时间与当前时间的时间差，单位：分钟 */
    private String updateMinutesDiff;

    /** 温度，默认单位：摄氏度 */
    private String temperature;

    /** 天气状况和图标的代码 */
    private String icon;

    /** 天气状况的文字描述，包括阴晴雨雪等天气状态的描述 */
    private String text;

    /** 风向 */
    private String windDirection;

    /** 风力等级 */
    private String windScale;

    /** 相对湿度，百分比数值 */
    private String humidity;

    /** 当前小时累计降水量，默认单位：毫米 */
    private String precipitation;

    /** 大气压强，默认单位：百帕 */
    private String pressure;
}
