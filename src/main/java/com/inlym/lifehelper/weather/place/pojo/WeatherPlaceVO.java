package com.inlym.lifehelper.weather.place.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 天气地点视图对象
 *
 * <h2>主要用途
 * <p>将 {@link com.inlym.lifehelper.weather.weatherplace.entity.WeatherPlace} 转化为客户端可用的视图对象。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/21
 * @since 1.5.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherPlaceVO {
    /** 地点 ID */
    private String id;

    /** 位置名称 */
    private String name;

    /** 所在地区，市 + 区，例如：“杭州市西湖区” */
    private String region;

    // ==============================  附带的天气数据  ===============================

    /** 天气图标的 URL 地址 */
    private String weatherIconUrl;

    /** 温度，默认单位：摄氏度 */
    private String temperature;

    /** 天气状况的文字描述，包括阴晴雨雪等天气状态的描述 */
    private String weatherText;
}
