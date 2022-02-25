package com.inlym.lifehelper.weather.weatherdata;

import com.inlym.lifehelper.external.heweather.HeMainService;
import com.inlym.lifehelper.weather.weatherdata.pojo.*;
import org.springframework.stereotype.Service;

/**
 * 天气数据服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-19
 * @since 1.0.0
 **/
@Service
public class WeatherDataService {
    private final HeMainService heMainService;

    public WeatherDataService(HeMainService heMainService) {
        this.heMainService = heMainService;
    }

    /**
     * 获取实时天气
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.0.0
     */
    public WeatherNow getWeatherNow(double longitude, double latitude) {
        return heMainService.getWeatherNow(longitude, latitude);
    }

    /**
     * 获取未来7天逐天天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.0.0
     */
    public WeatherDaily[] getWeather7D(double longitude, double latitude) {
        return heMainService.getWeatherDaily(longitude, latitude, "7d");
    }

    /**
     * 获取未来15天逐天天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.0.0
     */
    public WeatherDaily[] getWeather15D(double longitude, double latitude) {
        return heMainService.getWeatherDaily(longitude, latitude, "15d");
    }

    /**
     * 获取未来24小时逐小时天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.0.0
     */
    public WeatherHourly[] getWeather24H(double longitude, double latitude) {
        return heMainService.getWeatherHourly(longitude, latitude, "24h");
    }

    /**
     * 获取分钟级降水
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.0.0
     */
    public MinutelyRain getMinutely(double longitude, double latitude) {
        return heMainService.getMinutely(longitude, latitude);
    }

    /**
     * 获取当天天气生活指数
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.0.0
     */
    public IndicesDaily[] getIndices1D(double longitude, double latitude) {
        return heMainService.getIndicesDaily(longitude, latitude, "1d");
    }

    /**
     * 获取未来3天天气生活指数
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.0.0
     */
    public IndicesDaily[] getIndices3D(double longitude, double latitude) {
        return heMainService.getIndicesDaily(longitude, latitude, "3d");
    }

    /**
     * 获取实时空气质量
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.0.0
     */
    public AirNow getAirNow(double longitude, double latitude) {
        return heMainService.getAirNow(longitude, latitude);
    }

    /**
     * 获取未来5天空气质量预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.0.0
     */
    public AirDaily[] getAir5D(double longitude, double latitude) {
        return heMainService.getAirDaily(longitude, latitude);
    }
}
