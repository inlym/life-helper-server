package com.inlym.lifehelper.external.heweather;

import com.inlym.lifehelper.external.heweather.pojo.*;
import org.springframework.stereotype.Service;

/**
 * 和风天气服务
 * <p>
 * [主要用途]
 * 提供可对外调用的和风天气 API 方法。
 *
 * @author inlym
 * @date 2022-02-17
 **/
@Service
public class HeWeatherService {
    private final HeWeatherHttpService heWeatherHttpService;

    public HeWeatherService(HeWeatherHttpService heWeatherHttpService) {
        this.heWeatherHttpService = heWeatherHttpService;
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
    public HeWeatherNowResponse getWeatherNow(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heWeatherHttpService.getWeatherNow(location);
    }

    /**
     * 获取未来7天逐天天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public HeWeatherDailyResponse getWeather7D(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heWeatherHttpService.getWeatherDaily(location, "7d");
    }

    /**
     * 获取未来15天逐天天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public HeWeatherDailyResponse getWeather15D(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heWeatherHttpService.getWeatherDaily(location, "15d");
    }

    /**
     * 获取未来24小时逐小时天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public HeWeatherHourlyResponse getWeather24H(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heWeatherHttpService.getWeatherHourly(location, "24h");
    }

    /**
     * 获取分钟级降水
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public HeMinutelyResponse getMinutely(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heWeatherHttpService.getMinutely(location);
    }

    /**
     * 获取当天天气生活指数
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public HeIndicesResponse getIndices1D(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heWeatherHttpService.getIndicesDaily(location, "1d");
    }

    /**
     * 获取未来3天天气生活指数
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public HeIndicesResponse getIndices3D(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heWeatherHttpService.getIndicesDaily(location, "3d");
    }

    /**
     * 获取实时空气质量
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public HeAirNowResponse getAirNow(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heWeatherHttpService.getAirNow(location);
    }

    /**
     * 获取未来5天空气质量预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public HeAirDailyResponse getAir5D(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heWeatherHttpService.getAirDaily(location);
    }
}
