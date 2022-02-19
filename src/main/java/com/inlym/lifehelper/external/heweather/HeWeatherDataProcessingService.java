package com.inlym.lifehelper.external.heweather;

import com.inlym.lifehelper.external.heweather.pojo.*;
import com.inlym.lifehelper.weather.weather_data.pojo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 和风天气数据处理服务
 * <p>
 * [主要用途]
 * 将和风天气返回的数据做数据处理，封装为内部使用的天气数据。
 *
 * @author inlym
 * @date 2022-02-19
 **/
@Service
public class HeWeatherDataProcessingService {
    /** 存放和风天气图标的目录地址 */
    private static final String ICON_BASE_URL = "https://static.lifehelper.com.cn/qweather/icon/";

    /**
     * 生成 icon 图片的 URL 地址
     *
     * @param iconId icon 字段数据
     */
    private static String makeIconUrl(String iconId) {
        return ICON_BASE_URL + iconId + ".png";
    }

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
     * 处理实时天气响应数据
     *
     * @param res 和风天气实时天气响应数据
     */
    public WeatherNow processWeatherNow(HeWeatherNowResponse res) {
        WeatherNow now = new WeatherNow();

        BeanUtils.copyProperties(res.getNow(), now);
        now.setIconUrl(makeIconUrl(now.getIcon()));
        now.setUpdateMinutesDiff(String.valueOf(calculateMinutesDiff(res.getUpdateTime())));

        return now;
    }

    /**
     * 处理逐天天气预报响应数据
     *
     * @param res 和风天气逐天天气预报响应数据
     */
    public WeatherDaily[] processWeatherDaily(HeWeatherDailyResponse res) {
        HeWeatherDailyResponse.Daily[] daily = res.getDaily();

        WeatherDaily[] list = new WeatherDaily[daily.length];
        for (int i = 0; i < daily.length; i++) {
            HeWeatherDailyResponse.Daily source = daily[i];
            WeatherDaily target = new WeatherDaily();
            BeanUtils.copyProperties(source, target);
            target.setDate(source.getFxDate());
            target.setIconDayUrl(makeIconUrl(source.getIconDay()));
            target.setIconNightUrl(makeIconUrl(source.getIconNight()));

            list[i] = target;
        }

        return list;
    }

    /**
     * 处理逐小时天气预报响应数据
     *
     * @param res 和风天气逐小时天气预报响应数据
     */
    public WeatherHourly[] processWeatherHourly(HeWeatherHourlyResponse res) {
        HeWeatherHourlyResponse.Hourly[] hourly = res.getHourly();

        WeatherHourly[] list = new WeatherHourly[hourly.length];
        for (int i = 0; i < hourly.length; i++) {
            HeWeatherHourlyResponse.Hourly source = hourly[i];
            WeatherHourly target = new WeatherHourly();
            BeanUtils.copyProperties(source, target);
            target.setTime(source
                .getFxTime()
                .substring(11, 16));
            target.setIconUrl(makeIconUrl(source.getIcon()));

            list[i] = target;
        }

        return list;
    }

    /**
     * 处理分钟级降水响应数据
     *
     * @param res 和风天气分钟级降水响应数据
     */
    public MinutelyRain processMinutelyRain(HeMinutelyResponse res) {
        HeMinutelyResponse.Minutely[] minutely = res.getMinutely();
        MinutelyRain rain = new MinutelyRain();

        rain.setSummary(res.getSummary());
        rain.setUpdateMinutesDiff(String.valueOf(calculateMinutesDiff(res.getUpdateTime())));

        MinutelyRain.Minutely[] list = new MinutelyRain.Minutely[minutely.length];
        for (int i = 0; i < minutely.length; i++) {
            HeMinutelyResponse.Minutely source = minutely[i];
            MinutelyRain.Minutely target = new MinutelyRain.Minutely();
            BeanUtils.copyProperties(source, target);
            target.setTime(source
                .getFxTime()
                .substring(11, 16));

            list[i] = target;
        }
        rain.setMinutely(list);
        return rain;
    }

    /**
     * 处理天气生活指数响应数据
     *
     * @param res 和风天气天气生活指数响应数据
     */
    public IndicesDaily[] processIndicesDaily(HeIndicesResponse res) {
        HeIndicesResponse.Daily[] daily = res.getDaily();
        IndicesDaily[] list = new IndicesDaily[daily.length];

        for (int i = 0; i < daily.length; i++) {
            HeIndicesResponse.Daily source = daily[i];
            IndicesDaily target = new IndicesDaily();
            BeanUtils.copyProperties(source, target);

            // todo 待补充图片地址
            list[i] = target;
        }
        return list;
    }

    /**
     * 处理实时空气质量响应数据
     *
     * @param res 和风天气实时空气质量响应数据
     */
    public AirNow processAirNow(HeAirNowResponse res) {
        AirNow now = new AirNow();
        BeanUtils.copyProperties(res.getNow(), now);
        return now;
    }

    /**
     * 处理实时空气质量预报响应数据
     *
     * @param res 和风天气实时空气质量预报响应数据
     */
    public AirDaily[] processAirDaily(HeAirDailyResponse res) {
        HeAirDailyResponse.Daily[] daily = res.getDaily();
        AirDaily[] list = new AirDaily[daily.length];

        for (int i = 0; i < daily.length; i++) {
            HeAirDailyResponse.Daily source = daily[i];
            AirDaily target = new AirDaily();
            BeanUtils.copyProperties(source, target);
            target.setDate(source.getFxDate());

            list[i] = target;
        }
        return list;
    }
}
