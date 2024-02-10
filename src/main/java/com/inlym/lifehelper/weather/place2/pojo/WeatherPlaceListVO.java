package com.inlym.lifehelper.weather.place2.pojo;

import com.inlym.lifehelper.weather.data.pojo.BasicWeather;
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
    private List<WeatherPlaceVO> list;

    private IpLocatedPlace ipLocated;

    /** IP 定位地点 */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IpLocatedPlace {
        /** 地点名称，取“市”，例如“杭州市” */
        private String name;

        /** 附带的天气数据 */
        private BasicWeather weather;
    }
}
