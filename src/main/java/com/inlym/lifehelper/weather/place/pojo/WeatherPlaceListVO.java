package com.inlym.lifehelper.weather.place.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 天气地点列表响应数据
 *
 * <h2>主要用途
 * <p>封装天气地点列表响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/25
 * @since 1.5.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherPlaceListVO {
    List<WeatherPlaceVO> list;
}
