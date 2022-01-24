package com.inlym.lifehelper.external.hefeng;

import com.inlym.lifehelper.external.hefeng.model.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    /** icon 图标存放的目录地址 */
    private static final String ICON_BASE_URL = "https://static.lifehelper.com.cn/static/hefeng/";

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
     * 生成 icon 图片的 URL 地址
     *
     * @param iconId icon 字段数据
     */
    private static String makeIconUrl(String iconId) {
        return ICON_BASE_URL + "c1/" + iconId + ".svg";
    }

    /**
     * 生成天气生活指数的图片的 URL 地址
     *
     * @param type 生活指数类型ID
     */
    private static String makeIndicesImageUrl(String type) {
        return ICON_BASE_URL + "live/" + type + ".svg";
    }

    /**
     * 获取实时天气
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @SneakyThrows
    public WeatherNow getWeatherNow(double longitude, double latitude) {
        String location = joinCoordinate(longitude, latitude);
        HefengWeatherNowResponse res = hefengHttpService.getWeatherNow(location);
        HefengWeatherNowResponse.WeatherNow now = res.getNow();

        WeatherNow weatherNow = new WeatherNow();
        BeanUtils.copyProperties(now, weatherNow);

        // 新增的字段赋值
        weatherNow.setUpdateMinutesDiff(String.valueOf(calculateMinutesDiff(res.getUpdateTime())));
        weatherNow.setIconUrl(makeIconUrl(now.getIcon()));

        return weatherNow;
    }

    /**
     * 获取逐天天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param days      天数
     */
    @SneakyThrows
    public WeatherDailyForecast[] getWeatherDailyForecast(double longitude, double latitude, String days) {
        String location = joinCoordinate(longitude, latitude);
        HefengWeatherDailyForecastResponse res = hefengHttpService.getWeatherDailyForecast(location, days);
        HefengWeatherDailyForecastResponse.DailyForecast[] daily = res.getDaily();

        WeatherDailyForecast[] list = new WeatherDailyForecast[daily.length];
        for (int i = 0; i < daily.length; i++) {
            WeatherDailyForecast item = new WeatherDailyForecast();
            BeanUtils.copyProperties(daily[i], item);

            item.setDate(daily[i].getFxDate());
            item.setIconDayUrl(makeIconUrl(daily[i].getIconDay()));
            item.setIconNightUrl(makeIconUrl(daily[i].getIconNight()));

            list[i] = item;
        }

        return list;
    }

    /**
     * 获取逐小时天气预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param hours     小时数，支持 `24h`,`72h`,`168h`
     */
    @SneakyThrows
    public WeatherHourlyForecast[] getWeatherHourlyForecast(double longitude, double latitude, String hours) {
        String location = joinCoordinate(longitude, latitude);
        HefengWeatherHourlyForecastResponse res = hefengHttpService.getWeatherHourlyForecast(location, hours);
        HefengWeatherHourlyForecastResponse.HourlyForecast[] hourly = res.getHourly();

        WeatherHourlyForecast[] list = new WeatherHourlyForecast[hourly.length];
        for (int i = 0; i < hourly.length; i++) {
            WeatherHourlyForecast item = new WeatherHourlyForecast();
            BeanUtils.copyProperties(hourly[i], item);

            item.setTime(hourly[i].getFxTime());
            item.setIconUrl(makeIconUrl(hourly[i].getIcon()));

            list[i] = item;
        }

        return list;
    }

    /**
     * 获取分钟级降水
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @SneakyThrows
    public MinutelyRain getMinutelyRain(double longitude, double latitude) {
        String location = joinCoordinate(longitude, latitude);
        HefengGridWeatherMinutelyRainResponse res = hefengHttpService.getGridWeatherMinutelyRain(location);
        HefengGridWeatherMinutelyRainResponse.GridMinutelyForecast[] minutely = res.getMinutely();

        MinutelyRain minutelyRain = new MinutelyRain();
        minutelyRain.setSummary(res.getSummary());
        minutelyRain.setUpdateMinutesDiff(String.valueOf(calculateMinutesDiff(res.getUpdateTime())));

        MinutelyRain.MinutelyRainItem[] list = new MinutelyRain.MinutelyRainItem[minutely.length];
        for (int i = 0; i < res.getMinutely().length; i++) {
            MinutelyRain.MinutelyRainItem item = new MinutelyRain.MinutelyRainItem();
            item.setType(minutely[i].getType());
            item.setPrecipitation(minutely[i].getPrecip());
            item.setTime(minutely[i]
                .getFxTime()
                .substring(11, 16));

            list[i] = item;
        }

        minutelyRain.setList(list);

        return minutelyRain;
    }

    /**
     * 获取天气生活指数
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @SneakyThrows
    public WeatherIndices[] getIndices(double longitude, double latitude) {
        String location = joinCoordinate(longitude, latitude);
        HefengIndicesResponse res = hefengHttpService.getIndices(location);
        HefengIndicesResponse.DailyIndices[] daily = res.getDaily();

        WeatherIndices[] list = new WeatherIndices[daily.length];
        for (int i = 0; i < daily.length; i++) {
            WeatherIndices item = new WeatherIndices();
            BeanUtils.copyProperties(daily[i], item);

            item.setImageUrl(makeIndicesImageUrl(item.getType()));
            list[i] = item;
        }

        return list;
    }

    /**
     * 获取实时空气质量
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @SneakyThrows
    public WeatherAirNow getAirNow(double longitude, double latitude) {
        String location = joinCoordinate(longitude, latitude);
        HefengAirNowResponse res = hefengHttpService.getAirNow(location);

        WeatherAirNow weatherAirNow = new WeatherAirNow();
        BeanUtils.copyProperties(res.getNow(), weatherAirNow);

        return weatherAirNow;
    }

    /**
     * 获取空气质量预报
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @SneakyThrows
    public WeatherAirDailyForecast[] getAirDailyForecast(double longitude, double latitude) {
        String location = joinCoordinate(longitude, latitude);
        HefengAirDailyForecastResponse res = hefengHttpService.getAirDailyForecast(location);
        HefengAirDailyForecastResponse.AirDailyForecast[] daily = res.getDaily();

        WeatherAirDailyForecast[] list = new WeatherAirDailyForecast[daily.length];
        for (int i = 0; i < daily.length; i++) {
            WeatherAirDailyForecast item = new WeatherAirDailyForecast();
            BeanUtils.copyProperties(daily[i], item);
            item.setDate(daily[i].getFxDate());
            list[i] = item;
        }

        return list;
    }
}
