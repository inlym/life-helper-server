package com.weutil.weather.place.mapper;

import com.weutil.weather.place.entity.WeatherPlace;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 天气地点存储库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/16
 * @since 2.1.0
 **/
@Mapper
public interface WeatherPlaceMapper extends BaseMapper<WeatherPlace> {}
