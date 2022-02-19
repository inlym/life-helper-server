package com.inlym.lifehelper.weather.weather_data;

import com.inlym.lifehelper.external.heweather.HeWeatherDataProcessingService;
import com.inlym.lifehelper.external.heweather.HeWeatherService;
import com.inlym.lifehelper.external.heweather.pojo.HeWeatherNowResponse;
import com.inlym.lifehelper.weather.weather_data.pojo.WeatherNow;
import org.springframework.stereotype.Service;

/**
 * 天气数据服务
 *
 * @author inlym
 * @date 2022-02-19
 **/
@Service
public class WeatherDataService {
    private final HeWeatherService heWeatherService;

    private final HeWeatherDataProcessingService heWeatherDataProcessingService;

    public WeatherDataService(HeWeatherService heWeatherService, HeWeatherDataProcessingService heWeatherDataProcessingService) {
        this.heWeatherService = heWeatherService;
        this.heWeatherDataProcessingService = heWeatherDataProcessingService;
    }

    /**
     * 获取实时天气
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public WeatherNow getWeatherNow(double longitude, double latitude) {
        HeWeatherNowResponse data = heWeatherService.getWeatherNow(longitude, latitude);
        return heWeatherDataProcessingService.processWeatherNow(data);
    }
}
