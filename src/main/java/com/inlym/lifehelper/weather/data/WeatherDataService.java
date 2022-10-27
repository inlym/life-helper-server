package com.inlym.lifehelper.weather.data;

import com.inlym.lifehelper.external.heweather.HeDataService;
import com.inlym.lifehelper.weather.data.pojo.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 天气数据服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-19
 * @since 1.0.0
 **/
@Service
public class WeatherDataService {
    private final HeDataService heDataService;

    public WeatherDataService(HeDataService heDataService) {
        this.heDataService = heDataService;
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
     *
     * @since 1.0.0
     */
    public WeatherNow getWeatherNow(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heDataService.getWeatherNow(location);
    }

    /**
     * 获取未来7天逐天天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.0.0
     */
    public WeatherDailyItem[] getWeather7D(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heDataService.getWeatherDaily(location, "7d");
    }

    /**
     * 获取未来15天逐天天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.0.0
     */
    public WeatherDailyItem[] getWeather15D(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heDataService.getWeatherDailyWithAqi(location, "15d");
    }

    /**
     * 获取未来24小时逐小时天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.0.0
     */
    public WeatherHourlyItem[] getWeather24H(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heDataService.getWeatherHourly(location, "24h");
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
        String location = concatLocation(longitude, latitude);
        return heDataService.getMinutely(location);
    }

    /**
     * 获取当天天气生活指数
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.0.0
     */
    public IndicesItem[] getIndices1D(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heDataService.getIndicesDaily(location, "1d");
    }

    /**
     * 获取未来3天天气生活指数
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.0.0
     */
    public IndicesItem[] getIndices3D(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heDataService.getIndicesDaily(location, "3d");
    }

    /**
     * 获取天气灾害预警
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.2.0
     */
    public List<WarningNow> getWarningNow(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heDataService.getWarningNow(location);
    }

    /**
     * 获取天气灾害预警列表
     *
     * @param locationId 需要查询地区的 LocationID
     *
     * @since 1.5.0
     */
    public List<WarningNow> getWarningNow(String locationId) {
        return heDataService.getWarningNow(locationId);
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
        String location = concatLocation(longitude, latitude);
        return heDataService.getAirNow(location);
    }

    /**
     * 获取未来5天空气质量预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.0.0
     */
    public AirDailyItem[] getAir5D(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heDataService.getAirDaily(location);
    }
}
