package com.inlym.lifehelper.weather.data;

import com.inlym.lifehelper.external.heweather.HeDataService;
import com.inlym.lifehelper.weather.data.pojo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 天气数据服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-19
 * @since 1.0.0
 **/
@Service
@RequiredArgsConstructor
public class WeatherDataService {
    private final HeDataService heDataService;

    /**
     * 联结经纬度坐标，使其变成 `lng,lat` 的格式
     *
     * <h2>为什么要联结经纬度
     * <li>最终发起 HTTP 请求时，经纬度坐标需要以 `location=lng,lat` 的格式传递。
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.0.0
     */
    private static String concatLocation(double longitude, double latitude) {
        // 将数值向下取整到 2 位小数
        double lng = Math.floor(longitude * 100) / 100;
        double lat = Math.floor(latitude * 100) / 100;

        return lng + "," + lat;
    }

    /**
     * 根据经纬度获取和风天气的 LocationId
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.5.0
     */
    public String getUniqueLocationId(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        List<String> locations = heDataService.getGeoLocations(location);
        if (locations.size() == 1) {
            return locations.get(0);
        }

        throw new RuntimeException("经纬度错误，未获取到唯一的 LocationId");
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
     * 获取实时天气（异步）
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.5.0
     */
    @Async
    public CompletableFuture<WeatherNow> getWeatherNowAsync(double longitude, double latitude) {
        return CompletableFuture.completedFuture(getWeatherNow(longitude, latitude));
    }

    /**
     * 获取实时天气
     *
     * @param locationId 和风天气中的 LocationId
     *
     * @since 1.5.0
     */
    public WeatherNow getWeatherNow(String locationId) {
        return heDataService.getWeatherNow(locationId);
    }

    /**
     * 获取实时天气（异步）
     *
     * @param locationId 和风天气中的 LocationId
     *
     * @since 1.5.0
     */
    @Async
    public CompletableFuture<WeatherNow> getWeatherNowAsync(String locationId) {
        return CompletableFuture.completedFuture(getWeatherNow(locationId));
    }

    /**
     * 获取逐天天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param days      天数，支持 `3d`,`7d`,`10d`,`15d`,`30d`
     *
     * @since 1.5.0
     */
    public List<WeatherDaily> getWeatherDaily(double longitude, double latitude, String days) {
        String location = concatLocation(longitude, latitude);
        return heDataService.getWeatherDaily(location, days);
    }

    /**
     * 获取逐天天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param days      天数，支持 `3d`,`7d`,`10d`,`15d`,`30d`
     *
     * @since 1.5.0
     */
    @Async
    public CompletableFuture<List<WeatherDaily>> getWeatherDailyAsync(double longitude, double latitude, String days) {
        return CompletableFuture.completedFuture(getWeatherDaily(longitude, latitude, days));
    }

    /**
     * 获取逐天天气预报
     *
     * @param locationId 和风天气中的 LocationId
     * @param days       天数，支持 `3d`,`7d`,`10d`,`15d`,`30d`
     *
     * @since 1.5.0
     */
    public List<WeatherDaily> getWeatherDaily(String locationId, String days) {
        return heDataService.getWeatherDaily(locationId, days);
    }

    /**
     * 获取逐天天气预报
     *
     * @param locationId 和风天气中的 LocationId
     * @param days       天数，支持 `3d`,`7d`,`10d`,`15d`,`30d`
     *
     * @since 1.5.0
     */
    @Async
    public CompletableFuture<List<WeatherDaily>> getWeatherDailyAsync(String locationId, String days) {
        return CompletableFuture.completedFuture(getWeatherDaily(locationId, days));
    }

    /**
     * 获取逐小时天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param hours     小时数，支持 `24h`,`72h`,`168h`
     *
     * @since 1.5.0
     */
    public List<WeatherHourly> getWeatherHourly(double longitude, double latitude, String hours) {
        String location = concatLocation(longitude, latitude);
        return heDataService.getWeatherHourly(location, hours);
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
     * 获取天气灾害预警
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.5.0
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
     * 获取天气预警城市列表
     *
     * <h2>说明
     * <p>返回了一个 LocationId 的列表。
     *
     * @since 1.5.0
     */
    public List<String> getWarningList() {
        return heDataService.getWarningList();
    }

    /**
     * 获取天气生活指数
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param days      小时数，支持 `1d`,`3d`
     *
     * @since 1.5.0
     */
    public List<LivingIndex> getIndicesDaily(double longitude, double latitude, String days) {
        String location = concatLocation(longitude, latitude);
        return heDataService.getIndicesDaily(location, days);
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
    public List<AirDaily> getAirDaily(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return heDataService.getAirDaily(location);
    }
}
