package com.inlym.lifehelper.weather;

import com.inlym.lifehelper.external.hefeng.HefengService;
import com.inlym.lifehelper.external.hefeng.model.MinutelyRain;
import com.inlym.lifehelper.external.hefeng.model.WeatherDailyForecast;
import com.inlym.lifehelper.external.hefeng.model.WeatherHourlyForecast;
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
}
