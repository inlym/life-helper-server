package com.inlym.lifehelper.external.heweather;

import com.inlym.lifehelper.external.heweather.pojo.*;
import com.inlym.lifehelper.weather.weatherdata.pojo.*;
import com.inlym.lifehelper.weather.weatherplace.pojo.HeCity;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 和风天气数据服务
 *
 * <h2>说明
 * <p>将从 HTTP 请求获取的数据做二次处理，成为可以内部使用的有效数据。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/3/26
 **/
@Service
public final class HeDataService {
    private final HeHttpService heHttpService;

    public HeDataService(HeHttpService heHttpService) {
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
        return HeConstant.ICON_BASE_URL + iconId + ".png";
    }

    /**
     * 生成天气生活指数图片的 URL 地址
     *
     * @param typeId 生活指数类型 ID
     *
     * @since 1.0.0
     */
    private static String makeLiveImageUrl(String typeId) {
        return HeConstant.LIVE_IMAGE_BASE_URL + typeId + ".svg";
    }

    /**
     * 根据 icon 图标的 ID 计算所属的天气类型
     *
     * <h2>天气类型（来源于自行归纳）
     * <li>sun    - 晴 - 100, 150
     * <li>cloudy - 云 - 101 ~ 104 + 151 ~ 154
     * <li>rain   - 雨 - 300 ~ 399
     * <li>snow   - 雪 - 400 ~ 499
     * <li>haze   - 霾 - 500 ~ 599
     *
     * @param iconId icon 字段数据
     *
     * @since 1.0.0
     */
    private static String getWeatherTypeByIconId(String iconId) {
        int id = Integer.parseInt(iconId);

        if (id == 100 || id == 150) {
            return "sun";
        } else if (id > 100 && id < 200) {
            return "cloudy";
        } else if (id >= 300 && id < 400) {
            return "rain";
        } else if (id >= 400 && id < 500) {
            return "snow";
        } else if (id >= 500 && id < 600) {
            return "haze";
        } else {
            return "unknown";
        }
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
     * 查询和风天气城市
     *
     * @param location 需要查询地区的名称，支持文字、以英文逗号分隔的经度,纬度坐标（十进制，最多支持小数点后两位）、LocationID 或 Adcode（仅限中国城市）
     *
     * @since 1.0.0
     */
    public HeCity[] searchCities(String location) {
        HeCityLookupResponse data = heHttpService.searchCities(location);

        String SUCCESS_CODE = "200";

        if (!SUCCESS_CODE.equals(data.getCode())) {
            return new HeCity[0];
        }

        HeCity[] cities = new HeCity[data.getLocation().length];
        for (int i = 0; i < data.getLocation().length; i++) {
            HeCity target = new HeCity();
            HeCityLookupResponse.City source = data.getLocation()[i];
            BeanUtils.copyProperties(source, target);
            target.setLongitude(Double.valueOf(source.getLon()));
            target.setLatitude(Double.valueOf(source.getLat()));
            cities[i] = target;
        }

        return cities;
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
        now.setType(getWeatherTypeByIconId(now.getIcon()));

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
    @SneakyThrows
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

            // 天气描述
            if (source
                .getTextDay()
                .equals(source.getTextNight())) {
                target.setText(source.getTextDay());
            } else {
                target.setText(source.getTextDay() + "转" + source.getTextNight());
            }

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
    @SneakyThrows
    public WeatherHourly[] getWeatherHourly(String location, String hours) {
        HeWeatherHourlyResponse res = heHttpService.getWeatherHourly(location, hours);
        HeWeatherHourlyResponse.Hourly[] hourly = res.getHourly();

        WeatherHourly[] list = new WeatherHourly[hourly.length];
        for (int i = 0; i < hourly.length; i++) {
            HeWeatherHourlyResponse.Hourly source = hourly[i];
            WeatherHourly target = new WeatherHourly();
            BeanUtils.copyProperties(source, target);

            // 待解析的时间格式示例："2021-02-16T16:00+08:00"
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm+08:00");
            target.setTime(sdf.parse(source.getFxTime()));

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
    public IndicesItem[] getIndicesDaily(String location, String days) {
        HeIndicesResponse res = heHttpService.getIndicesDaily(location, days);
        HeIndicesResponse.Daily[] daily = res.getDaily();
        IndicesItem[] list = new IndicesItem[daily.length];

        for (int i = 0; i < daily.length; i++) {
            HeIndicesResponse.Daily source = daily[i];
            IndicesItem target = new IndicesItem();
            BeanUtils.copyProperties(source, target);
            target.setImageUrl(makeLiveImageUrl(target.getType()));

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
