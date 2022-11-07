package com.inlym.lifehelper.weather.data;

import com.inlym.lifehelper.external.heweather.HeDataService;
import com.inlym.lifehelper.weather.data.pojo.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
     * 辅助方法：合并（未完成）的逐日天气预报和逐日空气质量预报
     *
     * @param weatherDaily （未完成）的逐日天气预报
     * @param airDaily     （未完成）的逐日空气质量预报
     *
     * @since 1.5.0
     */
    @SneakyThrows
    private static List<WeatherDaily> mergeCompletableWeatherDailyAndAir(CompletableFuture<List<WeatherDaily>> weatherDaily, CompletableFuture<List<AirDaily>> airDaily) {
        CompletableFuture
            .allOf(weatherDaily, airDaily)
            .join();

        List<WeatherDaily> daily = weatherDaily.get();
        List<AirDaily> airDailyList = airDaily.get();

        // 备注（2022.11.05）
        // 这一段双重循环的含义是将相同日期的空气质量预报附在逐天天气预报上
        // 应该有优化的空间，后续再考虑
        for (WeatherDaily weather : daily) {
            for (AirDaily air : airDailyList) {
                if (weather
                    .getDate()
                    .isEqual(air.getDate())) {
                    weather.setAir(air);
                }
            }
        }

        return daily;
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
     * 获取逐小时天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param hours     小时数，支持 `24h`,`72h`,`168h`
     *
     * @since 1.5.0
     */
    @Async
    public CompletableFuture<List<WeatherHourly>> getWeatherHourlyAsync(double longitude, double latitude, String hours) {
        return CompletableFuture.completedFuture(getWeatherHourly(longitude, latitude, hours));
    }

    /**
     * 获取逐小时天气预报
     *
     * @param locationId 和风天气中的 LocationId
     * @param hours      小时数，支持 `24h`,`72h`,`168h`
     *
     * @since 1.5.0
     */
    public List<WeatherHourly> getWeatherHourly(String locationId, String hours) {
        return heDataService.getWeatherHourly(locationId, hours);
    }

    /**
     * 获取逐小时天气预报
     *
     * @param locationId 和风天气中的 LocationId
     * @param hours      小时数，支持 `24h`,`72h`,`168h`
     *
     * @since 1.5.0
     */
    @Async
    public CompletableFuture<List<WeatherHourly>> getWeatherHourlyAsync(String locationId, String hours) {
        return CompletableFuture.completedFuture(getWeatherHourly(locationId, hours));
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
     * 获取分钟级降水
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.5.0
     */
    @Async
    public CompletableFuture<MinutelyRain> getMinutelyAsync(double longitude, double latitude) {
        return CompletableFuture.completedFuture(getMinutely(longitude, latitude));
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
     * 获取天气灾害预警
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.5.0
     */
    @Async
    public CompletableFuture<List<WarningNow>> getWarningNowAsync(double longitude, double latitude) {
        return CompletableFuture.completedFuture(getWarningNow(longitude, latitude));
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
     * 获取天气灾害预警列表
     *
     * @param locationId 需要查询地区的 LocationID
     *
     * @since 1.5.0
     */
    @Async
    public CompletableFuture<List<WarningNow>> getWarningNowAsync(String locationId) {
        return CompletableFuture.completedFuture(getWarningNow(locationId));
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
     * 获取天气生活指数（异步）
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param days      小时数，支持 `1d`,`3d`
     *
     * @since 1.6.0
     */
    @Async
    public CompletableFuture<List<LivingIndex>> getIndicesDailyAsync(double longitude, double latitude, String days) {
        String location = concatLocation(longitude, latitude);
        return CompletableFuture.completedFuture(heDataService.getIndicesDaily(location, days));
    }

    /**
     * 获取天气生活指数
     *
     * @param locationId 需要查询地区的 LocationID
     * @param days       小时数，支持 `1d`,`3d`
     *
     * @since 1.5.0
     */
    public List<LivingIndex> getIndicesDaily(String locationId, String days) {
        return heDataService.getIndicesDaily(locationId, days);
    }

    /**
     * 获取天气生活指数
     *
     * @param locationId 需要查询地区的 LocationID
     * @param days       小时数，支持 `1d`,`3d`
     *
     * @since 1.5.0
     */
    @Async
    public CompletableFuture<List<LivingIndex>> getIndicesDailyAsync(String locationId, String days) {
        return CompletableFuture.completedFuture(heDataService.getIndicesDaily(locationId, days));
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
     * 获取实时空气质量
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.5.0
     */
    @Async
    public CompletableFuture<AirNow> getAirNowAsync(double longitude, double latitude) {
        return CompletableFuture.completedFuture(getAirNow(longitude, latitude));
    }

    /**
     * 获取实时空气质量
     *
     * @param locationId 需要查询地区的 LocationID
     *
     * @since 1.5.0
     */
    public AirNow getAirNow(String locationId) {
        return heDataService.getAirNow(locationId);
    }

    /**
     * 获取实时空气质量
     *
     * @param locationId 需要查询地区的 LocationID
     *
     * @since 1.5.0
     */
    @Async
    public CompletableFuture<AirNow> getAirNowAsync(String locationId) {
        return CompletableFuture.completedFuture(getAirNow(locationId));
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

    /**
     * 获取未来5天空气质量预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @since 1.0.0
     */
    @Async
    public CompletableFuture<List<AirDaily>> getAirDailyAsync(double longitude, double latitude) {
        return CompletableFuture.completedFuture(getAirDaily(longitude, latitude));
    }

    /**
     * 获取未来5天空气质量预报
     *
     * @param locationId 需要查询地区的 LocationID
     *
     * @since 1.5.0
     */
    public List<AirDaily> getAirDaily(String locationId) {
        return heDataService.getAirDaily(locationId);
    }

    /**
     * 获取未来5天空气质量预报
     *
     * @param locationId 需要查询地区的 LocationID
     *
     * @since 1.5.0
     */
    @Async
    public CompletableFuture<List<AirDaily>> getAirDailyAsync(String locationId) {
        return CompletableFuture.completedFuture(getAirDaily(locationId));
    }

    /**
     * 获取包含逐天空气质量预报的逐天天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param days      逐日天气预报的天数，支持 `3d`,`7d`,`10d`,`15d`,`30d`
     *
     * @since 1.5.0
     */
    public List<WeatherDaily> getWeatherDailyWithAir(double longitude, double latitude, String days) {
        CompletableFuture<List<WeatherDaily>> weatherDaily = getWeatherDailyAsync(longitude, latitude, days);
        CompletableFuture<List<AirDaily>> airDaily = getAirDailyAsync(longitude, latitude);
        return mergeCompletableWeatherDailyAndAir(weatherDaily, airDaily);
    }

    /**
     * 获取包含逐天空气质量预报的逐天天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param days      逐日天气预报的天数，支持 `3d`,`7d`,`10d`,`15d`,`30d`
     *
     * @since 1.5.0
     */
    @Async
    public CompletableFuture<List<WeatherDaily>> getWeatherDailyWithAirAsync(double longitude, double latitude, String days) {
        CompletableFuture<List<WeatherDaily>> weatherDaily = getWeatherDailyAsync(longitude, latitude, days);
        CompletableFuture<List<AirDaily>> airDaily = getAirDailyAsync(longitude, latitude);
        return CompletableFuture.completedFuture(mergeCompletableWeatherDailyAndAir(weatherDaily, airDaily));
    }

    /**
     * 获取包含逐天空气质量预报的逐天天气预报
     *
     * @param locationId 需要查询地区的 LocationID
     * @param days       逐日天气预报的天数，支持 `3d`,`7d`,`10d`,`15d`,`30d`
     *
     * @since 1.5.0
     */
    public List<WeatherDaily> getWeatherDailyWithAir(String locationId, String days) {
        CompletableFuture<List<WeatherDaily>> weatherDaily = getWeatherDailyAsync(locationId, days);
        CompletableFuture<List<AirDaily>> airDaily = getAirDailyAsync(locationId);
        return mergeCompletableWeatherDailyAndAir(weatherDaily, airDaily);
    }

    /**
     * 获取包含逐天空气质量预报的逐天天气预报
     *
     * @param locationId 需要查询地区的 LocationID
     * @param days       逐日天气预报的天数，支持 `3d`,`7d`,`10d`,`15d`,`30d`
     *
     * @since 1.5.0
     */
    @Async
    public CompletableFuture<List<WeatherDaily>> getWeatherDailyWithAirAsync(String locationId, String days) {
        CompletableFuture<List<WeatherDaily>> weatherDaily = getWeatherDailyAsync(locationId, days);
        CompletableFuture<List<AirDaily>> airDaily = getAirDailyAsync(locationId);
        return CompletableFuture.completedFuture(mergeCompletableWeatherDailyAndAir(weatherDaily, airDaily));
    }
}
