package com.inlym.lifehelper.external.hefeng;

import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.external.hefeng.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 和风天气服务
 * <p>
 * [主要用途]
 * 提供可对外调用的和风天气 API 方法。
 * <p>
 * [注意事项]
 * 1. 外部（项目内其他位置）应使用当前类，不允许直接使用 `HefengHttpService` 类。
 *
 * @author inlym
 * @since 2022-01-16 14:33
 **/
@Service
@Slf4j
public class HefengService {
    private final HefengHttpService hefengHttpService;

    public HefengService(HefengHttpService hefengHttpService) {this.hefengHttpService = hefengHttpService;}

    /**
     * 计算当前时间与指定时间的时间差（分钟数）
     *
     * @param timeText 时间的文本格式，范例 "2022-01-21T19:35+08:00"
     */
    private static long calculateMinutesDiff(String timeText) {
        Date target = Date.from(OffsetDateTime
            .parse(timeText)
            .toInstant());

        long diff = System.currentTimeMillis() - target.getTime();

        return TimeUnit.MILLISECONDS.toMinutes(diff);
    }

    /**
     * 联结经纬度坐标，使其变成 `lng,lat` 的格式
     * <p>
     * [为什么要联结经纬度]
     * 最终发起 HTTP 请求时，经纬度坐标需要以 `location=lng,lat` 的格式传递。
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    private static String joinCoordinate(double longitude, double latitude) {
        // 将数值向下取整到 2 位小数
        double lng = Math.floor(longitude * 100) / 100;
        double lat = Math.floor(latitude * 100) / 100;

        return lng + "," + lat;
    }

    /**
     * 获取实时天气
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public WeatherNow getWeatherNow(double longitude, double latitude) throws ExternalHttpRequestException {
        String location = joinCoordinate(longitude, latitude);
        HefengWeatherNowResponse res = hefengHttpService.getWeatherNow(location);
        HefengWeatherNowResponse.WeatherNow now = res.getNow();

        WeatherNow weatherNow = new WeatherNow();
        weatherNow.setUpdateMinutesDiff(String.valueOf(calculateMinutesDiff(res.getUpdateTime())));
        weatherNow.setTemperature(now.getTemp());
        weatherNow.setIcon(now.getIcon());
        weatherNow.setText(now.getText());
        weatherNow.setWindDirection(now.getWindDir());
        weatherNow.setWindScale(now.getWindScale());
        weatherNow.setHumidity(now.getHumidity());
        weatherNow.setPrecipitation(now.getPrecip());
        weatherNow.setPressure(now.getPressure());

        return weatherNow;
    }

    /**
     * 获取格点实时天气
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public WeatherNow getGridWeatherNow(double longitude, double latitude) throws ExternalHttpRequestException {
        String location = joinCoordinate(longitude, latitude);
        HefengGridWeatherNowResponse res = hefengHttpService.getGridWeatherNow(location);
        HefengGridWeatherNowResponse.GridWeatherNow now = res.getNow();

        WeatherNow weatherNow = new WeatherNow();
        weatherNow.setUpdateMinutesDiff(String.valueOf(calculateMinutesDiff(res.getUpdateTime())));
        weatherNow.setTemperature(now.getTemp());
        weatherNow.setIcon(now.getIcon());
        weatherNow.setText(now.getText());
        weatherNow.setWindDirection(now.getWindDir());
        weatherNow.setWindScale(now.getWindScale());
        weatherNow.setHumidity(now.getHumidity());
        weatherNow.setPrecipitation(now.getPrecip());
        weatherNow.setPressure(now.getPressure());

        return weatherNow;
    }

    /**
     * 获取未来 7 天格点逐天天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public HefengGridWeatherDailyForecastResponse getGridWeatherDailyForecastFor7d(double longitude, double latitude) throws Exception {
        String location = joinCoordinate(longitude, latitude);
        return hefengHttpService.getGridWeatherDailyForecast(location, "7d");
    }

    /**
     * 获取未来 24 小时格点逐小时天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public HefengGridWeatherHourlyForecastResponse getGridWeatherHourlyForecastFor24h(double longitude, double latitude) throws Exception {
        String location = joinCoordinate(longitude, latitude);
        return hefengHttpService.getGridWeatherHourlyForecast(location, "24h");
    }

    /**
     * 获取未来 72 小时格点逐小时天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public HefengGridWeatherHourlyForecastResponse getGridWeatherHourlyForecastFor72h(double longitude, double latitude) throws Exception {
        String location = joinCoordinate(longitude, latitude);
        return hefengHttpService.getGridWeatherHourlyForecast(location, "72h");
    }
}
