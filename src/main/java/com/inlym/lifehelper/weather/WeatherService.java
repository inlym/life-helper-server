package com.inlym.lifehelper.weather;

import com.inlym.lifehelper.external.hefeng.HefengService;
import com.inlym.lifehelper.external.hefeng.model.WeatherNow;
import org.springframework.stereotype.Service;

/**
 * 天气数据服务
 *
 * @author inlym
 * @since 2022-01-21 23:37
 **/
@Service
public class WeatherService {
    private final HefengService hefengService;

    public WeatherService(HefengService hefengService) {this.hefengService = hefengService;}

    /**
     * 获取实时天气
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public WeatherNow getWeatherNow(double longitude, double latitude) {
        return hefengService.getWeatherNow(longitude, latitude);
    }
}
