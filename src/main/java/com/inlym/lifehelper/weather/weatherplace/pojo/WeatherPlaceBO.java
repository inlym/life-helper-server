package com.inlym.lifehelper.weather.weatherplace.pojo;

import lombok.Data;

/**
 * 天气地点业务对象
 *
 * <h2>说明
 * <p>用于客户端展示天气地点列表项，而非直接展示天气地点实体
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/4/1
 * @since 1.0.0
 **/
@Data
public class WeatherPlaceBO {
    /** 主键 ID */
    private Integer id;

    /** 地点名称 */
    private String name;

    /** 所在地区，市 + 区，例如：“杭州市西湖区” */
    private String region;
}
