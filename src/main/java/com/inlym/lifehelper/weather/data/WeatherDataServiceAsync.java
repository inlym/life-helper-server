package com.inlym.lifehelper.weather.data;

import com.inlym.lifehelper.weather.data.pojo.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 天气数据服务（异步）
 *
 * @author inlym
 * @date 2022-02-19
 **/
@Service
public class WeatherDataServiceAsync {
    private final WeatherDataService weatherDataService;

    public WeatherDataServiceAsync(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    /**
     * 获取实时天气
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @Async
    public CompletableFuture<WeatherNow> getWeatherNow(double longitude, double latitude) {
        return CompletableFuture.completedFuture(weatherDataService.getWeatherNow(longitude, latitude));
    }

    /**
     * 获取未来15天逐天天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @Async
    public CompletableFuture<WeatherDaily[]> getWeather15D(double longitude, double latitude) {
        return CompletableFuture.completedFuture(weatherDataService.getWeather15Days(longitude, latitude));
    }

    /**
     * 获取未来24小时逐小时天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @Async
    public CompletableFuture<WeatherHourly[]> getWeather24H(double longitude, double latitude) {
        return CompletableFuture.completedFuture(weatherDataService.getWeather24H(longitude, latitude));
    }

    /**
     * 获取分钟级降水
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @Async
    public CompletableFuture<MinutelyRain> getMinutely(double longitude, double latitude) {
        return CompletableFuture.completedFuture(weatherDataService.getMinutely(longitude, latitude));
    }

    /**
     * 获取当天天气生活指数
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @Async
    public CompletableFuture<IndicesItem[]> getIndices1D(double longitude, double latitude) {
        return CompletableFuture.completedFuture(weatherDataService.getIndices1D(longitude, latitude));
    }

    /**
     * 获取未来3天天气生活指数
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @Async
    public CompletableFuture<IndicesItem[]> getIndices3D(double longitude, double latitude) {
        return CompletableFuture.completedFuture(weatherDataService.getIndices3D(longitude, latitude));
    }

    /**
     * 获取天气灾害预警
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.2.0
     */
    public CompletableFuture<List<WarningNow>> getWarningNow(double longitude, double latitude) {
        return CompletableFuture.completedFuture(weatherDataService.getWarningNow(longitude, latitude));
    }

    /**
     * 获取实时空气质量
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @Async
    public CompletableFuture<AirNow> getAirNow(double longitude, double latitude) {
        return CompletableFuture.completedFuture(weatherDataService.getAirNow(longitude, latitude));
    }

    /**
     * 获取未来5天空气质量预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @Async
    public CompletableFuture<AirDaily[]> getAir5D(double longitude, double latitude) {
        return CompletableFuture.completedFuture(weatherDataService.getAir5D(longitude, latitude));
    }
}
