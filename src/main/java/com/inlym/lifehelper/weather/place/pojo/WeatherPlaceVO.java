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

    /** 所属用户 ID */
    private Integer userId;

    /** 位置名称 */
    private String name;

    /** 详细地址 */
    private String address;

    /** 坐标点所在省 */
    private String province;

    /** 坐标点所在市 */
    private String city;

    /** 坐标点所在区 */
    private String district;
}
