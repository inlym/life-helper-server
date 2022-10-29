package com.inlym.lifehelper.weather.data.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 格点实时天气数据
 *
 * <h2>说明
 * <p>主要字段来源于和风天气 API 给的数据，对部分字段做了二次处理形成了新的字段。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/22
 * @see <a href="https://dev.qweather.com/docs/api/grid-weather/grid-weather-now/">格点实时天气</a>
 * @since 1.5.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GridWeatherNow {
    // ============================== 二次处理后新增的字段 ==============================

    /** 图标的 URL 地址 */
    private String iconUrl;

    /** 自行归纳的天气类型 */
    private String type;

    private Wind wind;

    // =============================== 和风天气原有的字段 ==============================

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
}
