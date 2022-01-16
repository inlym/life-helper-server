package com.inlym.lifehelper.external.hefeng;

import com.inlym.lifehelper.external.hefeng.model.HefengGridWeatherDailyForecastResponse;
import com.inlym.lifehelper.external.hefeng.model.HefengGridWeatherHourlyForecastResponse;
import com.inlym.lifehelper.external.hefeng.model.HefengGridWeatherNowResponse;
import org.springframework.stereotype.Service;

/**
 * 和风天气服务
 * <p>
 * [主要用途]
 * 提供可对外调用的和风天气 API 方法。
 * <p>
 * [注意事项]
 * 1. 外部（项目内其他位置）应使用当前类，不允许直接使用 `HefengHttpService` 类。
 * 2. 当前类主要对入参做了处理，目的是方便外部调用和参数检验，未对响应数据本身做处理。
 *
 * @author inlym
 * @since 2022-01-16 14:33
 **/
@Service
public class HefengService {
    private final HefengHttpService hefengHttpService;

    public HefengService(HefengHttpService hefengHttpService) {this.hefengHttpService = hefengHttpService;}

    /**
     * 联结经纬度坐标，使其变成 `lng,lat` 的格式
     * <p>
     * [为什么要联结经纬度]
     * 最终发起 HTTP 请求时，经纬度坐标需要以 `location=lng,lat` 的格式传递。
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    private String joinCoordinate(double longitude, double latitude) {
        // 将数值向下取整到 2 位小数
        double lng = Math.floor(longitude * 100) / 100;
        double lat = Math.floor(latitude * 100) / 100;

        return lng + "," + lat;
    }

    /**
     * 获取格点实时天气
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public HefengGridWeatherNowResponse getGridWeatherNow(double longitude, double latitude) throws Exception {
        String location = joinCoordinate(longitude, latitude);
        return hefengHttpService.getGridWeatherNow(location);
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
