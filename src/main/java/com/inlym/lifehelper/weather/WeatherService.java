package com.inlym.lifehelper.weather;

import com.inlym.lifehelper.external.hefeng.HefengService;
import com.inlym.lifehelper.external.hefeng.model.*;
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

    /**
     * 获取未来15天的逐天天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public WeatherDailyForecast[] get15DaysWeatherDailyForecast(double longitude, double latitude) {
        return hefengService.getWeatherDailyForecast(longitude, latitude, "15d");
    }

    /**
     * 获取未来24小时的逐小时天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public WeatherHourlyForecast[] get24HoursWeatherHourlyForecast(double longitude, double latitude) {
        return hefengService.getWeatherHourlyForecast(longitude, latitude, "24h");
    }

    /**
     * 获取分钟级降水
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public MinutelyRain getMinutelyRain(double longitude, double latitude) {
        return hefengService.getMinutelyRain(longitude, latitude);
    }

    /**
     * 获取天气生活指数
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public WeatherIndices[] getIndices(double longitude, double latitude) {
        return hefengService.getIndices(longitude, latitude);
    }

    /**
     * 获取实时空气质量
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public WeatherAirNow getAirNow(double longitude, double latitude) {
        return hefengService.getAirNow(longitude, latitude);
    }

    /**
     * 获取空气质量预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public WeatherAirDailyForecast[] getAirDailyForecast(double longitude, double latitude) {
        return hefengService.getAirDailyForecast(longitude, latitude);
    }
}
