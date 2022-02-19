package com.inlym.lifehelper.weather.weather_data;

import com.inlym.lifehelper.external.heweather.HeWeatherDataProcessingService;
import com.inlym.lifehelper.external.heweather.HeWeatherService;
import com.inlym.lifehelper.external.heweather.pojo.*;
import com.inlym.lifehelper.weather.weather_data.pojo.*;
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
        HeWeatherNowResponse res = heWeatherService.getWeatherNow(longitude, latitude);
        return heWeatherDataProcessingService.processWeatherNow(res);
    }

    /**
     * 获取未来7天逐天天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public WeatherDaily[] getWeather7D(double longitude, double latitude) {
        HeWeatherDailyResponse res = heWeatherService.getWeather7D(longitude, latitude);
        return heWeatherDataProcessingService.processWeatherDaily(res);
    }

    /**
     * 获取未来15天逐天天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public WeatherDaily[] getWeather15D(double longitude, double latitude) {
        HeWeatherDailyResponse res = heWeatherService.getWeather15D(longitude, latitude);
        return heWeatherDataProcessingService.processWeatherDaily(res);
    }

    /**
     * 获取未来24小时逐小时天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public WeatherHourly[] getWeather24H(double longitude, double latitude) {
        HeWeatherHourlyResponse res = heWeatherService.getWeather24H(longitude, latitude);
        return heWeatherDataProcessingService.processWeatherHourly(res);
    }

    /**
     * 获取分钟级降水
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public MinutelyRain getMinutely(double longitude, double latitude) {
        HeMinutelyResponse res = heWeatherService.getMinutely(longitude, latitude);
        return heWeatherDataProcessingService.processMinutelyRain(res);
    }

    /**
     * 获取当天天气生活指数
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public IndicesDaily[] getIndices1D(double longitude, double latitude) {
        HeIndicesResponse res = heWeatherService.getIndices1D(longitude, latitude);
        return heWeatherDataProcessingService.processIndicesDaily(res);
    }

    /**
     * 获取未来3天天气生活指数
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public IndicesDaily[] getIndices3D(double longitude, double latitude) {
        HeIndicesResponse res = heWeatherService.getIndices3D(longitude, latitude);
        return heWeatherDataProcessingService.processIndicesDaily(res);
    }

    /**
     * 获取实时空气质量
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public AirNow getAirNow(double longitude, double latitude) {
        HeAirNowResponse res = heWeatherService.getAirNow(longitude, latitude);
        return heWeatherDataProcessingService.processAirNow(res);
    }

    /**
     * 获取未来5天空气质量预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public AirDaily[] getAir5D(double longitude, double latitude) {
        HeAirDailyResponse res = heWeatherService.getAir5D(longitude, latitude);
        return heWeatherDataProcessingService.processAirDaily(res);
    }
}
