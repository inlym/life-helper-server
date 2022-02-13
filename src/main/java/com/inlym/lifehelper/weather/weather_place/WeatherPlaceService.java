package com.inlym.lifehelper.weather.weather_place;

import com.inlym.lifehelper.weather.weather_place.entity.WeatherPlace;
import com.inlym.lifehelper.weather.weather_place.mapper.WeatherPlaceMapper;
import com.inlym.lifehelper.weather.weather_place.pojo.WeixinChooseLocationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 天气地点服务类
 *
 * @author inlym
 * @date 2022-02-13 20:48
 **/
@Service
@Slf4j
public class WeatherPlaceService {
    private final WeatherPlaceMapper weatherPlaceMapper;

    public WeatherPlaceService(WeatherPlaceMapper weatherPlaceMapper) {this.weatherPlaceMapper = weatherPlaceMapper;}

    /**
     * 新增一个天气地点
     *
     * @param dto 前端传输的请求数据
     */
    public WeatherPlace add(int userId, WeixinChooseLocationDTO dto) {
        WeatherPlace place = new WeatherPlace();
        place.setUserId(userId);
        place.setName(dto.getName());
        place.setAddress(dto.getAddress());
        place.setLongitude(dto.getLongitude());
        place.setLatitude(dto.getLatitude());

        // 临时代码 - 开始
        place.setProvince("浙江省");
        place.setCity("杭州市");
        place.setDistrict("西湖区");
        // 临时代码 - 结束

        weatherPlaceMapper.insert(place);

        return place;
    }
}
