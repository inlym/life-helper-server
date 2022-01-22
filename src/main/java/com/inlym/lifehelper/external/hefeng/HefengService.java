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
     * 生成 icon 图片的网络地址
     *
     * @param iconId icon 字段数据
     */
    private static String makeIconUrl(String iconId) {
        return ICON_BASE_URL + "c1/" + iconId + ".svg";
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

        // 以下3个属性改了名称，因此需要额外进行赋值
        weatherNow.setTemperature(now.getTemp());
        weatherNow.setWindDirection(now.getWindDir());
        weatherNow.setPrecipitation(now.getPrecip());

        // 新增的字段赋值
        weatherNow.setUpdateMinutesDiff(String.valueOf(calculateMinutesDiff(res.getUpdateTime())));
        weatherNow.setIconUrl(makeIconUrl(now.getIcon()));

        return weatherNow;
    }

    /**
     * 获取格点实时天气
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    @SneakyThrows
    public WeatherNow getGridWeatherNow(double longitude, double latitude) {
        String location = joinCoordinate(longitude, latitude);
        HefengGridWeatherNowResponse res = hefengHttpService.getGridWeatherNow(location);
        HefengGridWeatherNowResponse.GridWeatherNow now = res.getNow();

        WeatherNow weatherNow = new WeatherNow();
        BeanUtils.copyProperties(now, weatherNow);

        // 以下3个属性改了名称，因此需要额外进行赋值
        weatherNow.setPrecipitation(now.getPrecip());
        weatherNow.setTemperature(now.getTemp());
        weatherNow.setWindDirection(now.getWindDir());

        // 新增的字段赋值
        weatherNow.setUpdateMinutesDiff(String.valueOf(calculateMinutesDiff(res.getUpdateTime())));
        weatherNow.setIconUrl(makeIconUrl(now.getIcon()));

        return weatherNow;
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
}
