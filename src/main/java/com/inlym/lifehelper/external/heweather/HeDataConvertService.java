package com.inlym.lifehelper.external.heweather;

import com.inlym.lifehelper.external.heweather.pojo.*;
import com.inlym.lifehelper.weather.weatherdata.pojo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 和风天气数据处理服务
 *
 * <h2>主要用途
 * <p>将从 HTTP 请求获取的响应数据做二次处理，返回内部可以使用的数据。
 *
 * <h2>注意事项
 * <li>仅处理响应数据，不对请求参数做处理。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-25
 * @since 1.0.0
 **/
@Service
public class HeDataConvertService {
    /** 存放和风天气图标的目录地址 */
    private static final String ICON_BASE_URL = "https://static.lifehelper.com.cn/qweather/icon/";

    private final HeHttpService heHttpService;

    public HeDataConvertService(HeHttpService heHttpService) {
        this.heHttpService = heHttpService;
    }

    /**
     * 生成 icon 图片的 URL 地址
     *
     * @param iconId icon 字段数据
     *
     * @since 1.0.0
     */
    private static String makeIconUrl(String iconId) {
        return ICON_BASE_URL + iconId + ".png";
    }

    /**
     * 计算当前时间与指定时间的时间差（分钟数）
     *
     * @param timeText 时间的文本格式，范例 "2022-01-21T19:35+08:00"
     *
     * @since 1.0.0
     */
    private static long calculateMinutesDiff(String timeText) {
        Date target = Date.from(OffsetDateTime
            .parse(timeText)
            .toInstant());

        long diff = System.currentTimeMillis() - target.getTime();

        return TimeUnit.MILLISECONDS.toMinutes(diff);
    }

    /**
     * 获取实时天气
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     *
     * @see HeHttpService#getWeatherNow
     * @since 1.0.0
     */
    public WeatherNow getWeatherNow(String location) {
        HeWeatherNowResponse res = heHttpService.getWeatherNow(location);

        WeatherNow now = new WeatherNow();
        BeanUtils.copyProperties(res.getNow(), now);
        now.setIconUrl(makeIconUrl(now.getIcon()));
        now.setUpdateMinutesDiff(String.valueOf(calculateMinutesDiff(res.getUpdateTime())));

        return now;
    }

    /**
     * 获取逐天天气预报
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     * @param days     天数，支持 `3d`,`7d`,`10d`,`15d`
     *
     * @see HeHttpService#getWeatherDaily
     * @since 1.0.0
     */
    public WeatherDaily[] getWeatherDaily(String location, String days) {
        HeWeatherDailyResponse res = heHttpService.getWeatherDaily(location, days);
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
     * 获取逐小时天气预报
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     * @param hours    小时数，支持 `24h`,`72h`,`168h`
     *
     * @see HeHttpService#getWeatherHourly
     * @since 1.0.0
     */
    public WeatherHourly[] getWeatherHourly(String location, String hours) {
        HeWeatherHourlyResponse res = heHttpService.getWeatherHourly(location, hours);
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
     * 获取分钟级降水
     *
     * @param location 需要查询地区的以英文逗号分隔的经度,纬度坐标（十进制，最多支持小数点后两位）
     *
     * @see HeHttpService#getWeatherHourly
     * @since 1.0.0
     */
    public MinutelyRain getMinutely(String location) {
        HeMinutelyResponse res = heHttpService.getMinutely(location);
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
     * 获取天气生活指数
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     * @param days     小时数，支持 `1d`,`3d`
     *
     * @see HeHttpService#getIndicesDaily
     * @since 1.0.0
     */
    public IndicesDaily[] getIndicesDaily(String location, String days) {
        HeIndicesResponse res = heHttpService.getIndicesDaily(location, days);
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
     * 获取实时空气质量
     *
     * @param location 需要查询地区的LocationID或以英文逗号分隔的经度,纬度坐标
     *
     * @see HeHttpService#getAirNow
     * @since 1.0.0
     */
    public AirNow getAirNow(String location) {
        HeAirNowResponse res = heHttpService.getAirNow(location);
        AirNow now = new AirNow();
        BeanUtils.copyProperties(res.getNow(), now);
        return now;
    }

    /**
     * 获取空气质量预报
     *
     * @param location 需要查询地区的LocationID或以英文逗号分隔的经度,纬度坐标
     *
     * @see HeHttpService#getAirDaily
     * @since 1.0.0
     */
    public AirDaily[] getAirDaily(String location) {
        HeAirDailyResponse res = heHttpService.getAirDaily(location);
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
