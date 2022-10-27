package com.inlym.lifehelper.weather.data;

import com.inlym.lifehelper.weather.data.pojo.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 混合天气数据服务
 *
 * @author inlym
 * @date 2022-02-19
 **/
@Service
public class WeatherMixedDataService {
    private final WeatherDataServiceAsync weatherDataServiceAsync;

    public WeatherMixedDataService(WeatherDataServiceAsync weatherDataServiceAsync) {
        this.weatherDataServiceAsync = weatherDataServiceAsync;
    }

    /**
     * 获取整合后的天气数据
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @SneakyThrows
    public Map<String, Object> getMixedWeatherData(double longitude, double latitude) {
        CompletableFuture<WeatherNow> now = weatherDataServiceAsync.getWeatherNow(longitude, latitude);
        CompletableFuture<WeatherDailyItem[]> f15d = weatherDataServiceAsync.getWeather15D(longitude, latitude);
        CompletableFuture<WeatherHourlyItem[]> f24h = weatherDataServiceAsync.getWeather24H(longitude, latitude);
        CompletableFuture<MinutelyRain> rain = weatherDataServiceAsync.getMinutely(longitude, latitude);
        CompletableFuture<IndicesItem[]> indices3d = weatherDataServiceAsync.getIndices3D(longitude, latitude);
        CompletableFuture<List<WarningNow>> warnings = weatherDataServiceAsync.getWarningNow(longitude, latitude);
        CompletableFuture<AirNow> airNow = weatherDataServiceAsync.getAirNow(longitude, latitude);

        CompletableFuture
            .allOf(now, f15d, f24h, rain, indices3d, warnings, airNow)
            .join();

        Map<String, Object> map = new HashMap<>(16);
        map.put("now", now.get());
        map.put("f15d", f15d.get());
        map.put("f24h", f24h.get());
        map.put("rain", rain.get());
        map.put("indices3d", indices3d.get());
        map.put("warnings", warnings.get());
        map.put("airNow", airNow.get());

        return map;
    }
}
