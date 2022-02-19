package com.inlym.lifehelper.weather.weather_data.old;

import com.inlym.lifehelper.external.hefeng.model.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * 天气服务类（异步）
 *
 * @author inlym
 * @since 2022-01-25 20:19
 **/
@Service
public class WeatherServiceAsync {
    private final WeatherService weatherService;

    public WeatherServiceAsync(WeatherService weatherService) {this.weatherService = weatherService;}

    /**
     * 获取实时天气
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @Async
    public CompletableFuture<WeatherNow> getWeatherNow(double longitude, double latitude) {
        return CompletableFuture.completedFuture(weatherService.getWeatherNow(longitude, latitude));
    }

    /**
     * 获取未来15天的逐天天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @Async
    public CompletableFuture<WeatherDailyForecast[]> get15DaysWeatherDailyForecast(double longitude, double latitude) {
        return CompletableFuture.completedFuture(weatherService.get15DaysWeatherDailyForecast(longitude, latitude));
    }

    /**
     * 获取未来24小时的逐小时天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @Async
    public CompletableFuture<WeatherHourlyForecast[]> get24HoursWeatherHourlyForecast(double longitude, double latitude) {
        return CompletableFuture.completedFuture(weatherService.get24HoursWeatherHourlyForecast(longitude, latitude));
    }

    /**
     * 获取分钟级降水
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @Async
    public CompletableFuture<MinutelyRain> getMinutelyRain(double longitude, double latitude) {
        return CompletableFuture.completedFuture(weatherService.getMinutelyRain(longitude, latitude));
    }

    /**
     * 获取天气生活指数
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @Async
    public CompletableFuture<WeatherIndices[]> getIndices(double longitude, double latitude) {
        return CompletableFuture.completedFuture(weatherService.getIndices(longitude, latitude));
    }

    /**
     * 获取实时空气质量
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @Async
    public CompletableFuture<WeatherAirNow> getAirNow(double longitude, double latitude) {
        return CompletableFuture.completedFuture(weatherService.getAirNow(longitude, latitude));
    }

    /**
     * 获取空气质量预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @Async
    public CompletableFuture<WeatherAirDailyForecast[]> getAirDailyForecast(double longitude, double latitude) {
        return CompletableFuture.completedFuture(weatherService.getAirDailyForecast(longitude, latitude));
    }
}
