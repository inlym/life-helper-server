package com.inlym.lifehelper.weather.weather_place.mapper;

import com.inlym.lifehelper.weather.weather_place.entity.WeatherPlace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author inlym
 * @date 2022-02-13 19:03
 **/
@Mapper
public interface WeatherPlaceMapper {
    /**
     * 插入1条天气地点
     *
     * @param weatherPlace 天气地点
     */
    void insert(@Param("place") WeatherPlace weatherPlace);
}
