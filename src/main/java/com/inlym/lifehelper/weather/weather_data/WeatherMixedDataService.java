package com.inlym.lifehelper.weather.weather_data;

import com.inlym.lifehelper.external.hefeng.model.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 混合天气数据服务
 *
 * @author inlym
 * @since 2022-01-25 20:30
 **/
@Service
public class WeatherMixedDataService {
    private final WeatherServiceAsync weatherServiceAsync;

    public WeatherMixedDataService(WeatherServiceAsync weatherServiceAsync) {this.weatherServiceAsync = weatherServiceAsync;}

    /**
     * 获取汇总的天气数据（用于一个接口输出）
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @SneakyThrows
    public Map<String, Object> getMixedWeather(double longitude, double latitude) {
        CompletableFuture<WeatherNow> now = weatherServiceAsync.getWeatherNow(longitude, latitude);
        CompletableFuture<WeatherDailyForecast[]> f15d = weatherServiceAsync.get15DaysWeatherDailyForecast(longitude, latitude);
        CompletableFuture<WeatherHourlyForecast[]> f24h = weatherServiceAsync.get24HoursWeatherHourlyForecast(longitude, latitude);
        CompletableFuture<MinutelyRain> rain = weatherServiceAsync.getMinutelyRain(longitude, latitude);
        CompletableFuture<WeatherIndices[]> indices = weatherServiceAsync.getIndices(longitude, latitude);
        CompletableFuture<WeatherAirNow> airNow = weatherServiceAsync.getAirNow(longitude, latitude);
        CompletableFuture<WeatherAirDailyForecast[]> air5d = weatherServiceAsync.getAirDailyForecast(longitude, latitude);

        CompletableFuture
            .allOf(now, f15d, f24h, rain, indices, airNow, air5d)
            .join();

        Map<String, Object> map = new HashMap<>(16);
        map.put("now", now.get());
        map.put("f15d", f15d.get());
        map.put("f24h", f24h.get());
        map.put("rain", rain.get());
        map.put("indices", indices.get());
        map.put("airNow", airNow.get());
        map.put("air5d", air5d.get());

        return map;
    }
}
