package com.inlym.lifehelper.external.heweather;

import com.inlym.lifehelper.weather.weatherdata.pojo.*;
import org.springframework.stereotype.Service;

/**
 * 和风天气服务
 *
 * <h2>主要用途
 * <p>封装内部使用的天气数据服务，同时对请求参数做二次封装。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-25
 * @since 1.0.0
 **/
@Service
public class HeMainService {
    private final HeDataConvertService heDataConvertService;

    public HeMainService(HeDataConvertService heDataConvertService) {
        this.heDataConvertService = heDataConvertService;
    }

    /**
     * 联结经纬度坐标，使其变成 `lng,lat` 的格式
     *
     * <h2>为什么要联结经纬度
     * <li>最终发起 HTTP 请求时，经纬度坐标需要以 `location=lng,lat` 的格式传递。
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    private static String concatLocation(double longitude, double latitude) {
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
    public WeatherNow getWeatherNow(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heDataConvertService.getWeatherNow(location);
    }

    /**
     * 获取逐天天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public WeatherDaily[] getWeatherDaily(double longitude, double latitude, String days) {
        String location = concatLocation(longitude, latitude);
        return heDataConvertService.getWeatherDaily(location, days);
    }

    /**
     * 获取逐小时天气
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public WeatherHourly[] getWeatherHourly(double longitude, double latitude, String hours) {
        String location = concatLocation(longitude, latitude);
        return heDataConvertService.getWeatherHourly(location, hours);
    }

    /**
     * 获取分钟级降水
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public MinutelyRain getMinutely(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heDataConvertService.getMinutely(location);
    }

    /**
     * 获取天气生活指数预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public IndicesDaily[] getIndicesDaily(double longitude, double latitude, String days) {
        String location = concatLocation(longitude, latitude);
        return heDataConvertService.getIndicesDaily(location, days);
    }

    /**
     * 获取实时空气质量
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public AirNow getAirNow(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heDataConvertService.getAirNow(location);
    }

    /**
     * 获取未来5天空气质量预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public AirDaily[] getAirDaily(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heDataConvertService.getAirDaily(location);
    }
}
