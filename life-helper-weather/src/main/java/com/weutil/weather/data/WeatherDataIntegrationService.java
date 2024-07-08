package com.weutil.weather.data;

import com.weutil.weather.data.pojo.*;
import com.weutil.weather.place.entity.WeatherPlace;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 天气数据整合服务
 *
 * <h2>主要用途
 * <p>将所有天气数据合并在一起，供控制器使用。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/30
 * @since 1.5.0
 **/
@Service
@RequiredArgsConstructor
public class WeatherDataIntegrationService {
    private final WeatherDataService weatherDataService;

    /**
     * 获取汇总后的天气数据
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.5.0
     */
    @SneakyThrows
    public WeatherDataVO getWeatherData(double longitude, double latitude) {
        CompletableFuture<WeatherNow> now = weatherDataService.getWeatherNowAsync(longitude, latitude);
        CompletableFuture<List<WeatherDaily>> daily = weatherDataService.getWeatherDailyWithAirAsync(longitude, latitude, "15d");
        CompletableFuture<List<WeatherHourly>> hourly = weatherDataService.getWeatherHourlyAsync(longitude, latitude, "24h");
        CompletableFuture<MinutelyRain> rain = weatherDataService.getMinutelyAsync(longitude, latitude);
        CompletableFuture<List<WarningNow>> warnings = weatherDataService.getWarningNowAsync(longitude, latitude);
        CompletableFuture<List<LivingIndex>> indices = weatherDataService.getIndicesDailyAsync(longitude, latitude, "3d");
        CompletableFuture<AirNow> airNow = weatherDataService.getAirNowAsync(longitude, latitude);

        CompletableFuture
                .allOf(now, daily, hourly, rain, warnings, indices, airNow)
                .join();

        return WeatherDataVO
                .builder()
                .now(now.get())
                .daily(daily.get())
                .hourly(hourly.get())
                .rain(rain.get())
                .warnings(warnings.get())
                .indices(indices.get())
                .airNow(airNow.get())
                .date(LocalDate.now())
                .build();
    }

    /**
     * 获取汇总后的天气数据
     *
     * @param place 天气地点
     *
     * @since 1.5.0
     */
    @SneakyThrows
    public WeatherDataVO getWeatherData(WeatherPlace place) {
        CompletableFuture<WeatherNow> now = weatherDataService.getWeatherNowAsync(place.getLocationId());
        CompletableFuture<List<WeatherDaily>> daily = weatherDataService.getWeatherDailyWithAirAsync(place.getLocationId(), "15d");
        CompletableFuture<List<WeatherHourly>> hourly = weatherDataService.getWeatherHourlyAsync(place.getLocationId(), "24h");
        CompletableFuture<MinutelyRain> rain = weatherDataService.getMinutelyAsync(place.getLongitude(), place.getLatitude());
        CompletableFuture<List<WarningNow>> warnings = weatherDataService.getWarningNowAsync(place.getLocationId());
        CompletableFuture<List<LivingIndex>> indices = weatherDataService.getIndicesDailyAsync(place.getLocationId(), "3d");
        CompletableFuture<AirNow> airNow = weatherDataService.getAirNowAsync(place.getLocationId());

        CompletableFuture
                .allOf(now, daily, hourly, rain, warnings, indices, airNow)
                .join();

        return WeatherDataVO
                .builder()
                .now(now.get())
                .daily(daily.get())
                .hourly(hourly.get())
                .rain(rain.get())
                .warnings(warnings.get())
                .indices(indices.get())
                .airNow(airNow.get())
                // 这一步与上个方法有差异
                .locationName(place.getName())
                .date(LocalDate.now())
                .build();
    }
}
