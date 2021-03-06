package com.inlym.lifehelper.external.heweather;

import com.inlym.lifehelper.external.heweather.pojo.*;
import com.inlym.lifehelper.weather.weatherdata.pojo.*;
import com.inlym.lifehelper.weather.weatherplace.pojo.HeCity;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

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
     * 预警等级转换为对应的数字字符串
     *
     * @param level 预警等级
     *
     * @since 1.2.0
     */
    private static String getWarningLevelNum(String level) {
        String red = "红色";
        String orange = "橙色";
        String yellow = "黄色";
        String blue = "蓝色";
        String white = "白色";

        if (red.equals(level)) {
            return "1";
        } else if (orange.equals(level)) {
            return "2";
        } else if (yellow.equals(level)) {
            return "3";
        } else if (blue.equals(level)) {
            return "4";
        } else if (white.equals(level)) {
            return "5";
        } else {
            return "0";
        }
    }

    /**
     * 生成天气预警图片的 URL 地址
     *
     * @param type  预警类型 ID
     * @param level 预警等级
     *
     * @since 1.2.0
     */
    private static String makeWarningImageUrl(String type, String level) {
        String levelNum = getWarningLevelNum(level);
        return HeConstant.WARNING_IMAGE_BASE_URL + type + "-" + levelNum + ".png";
    }

    /**
     * 生成天气预警图标的 URL 地址
     *
     * @param type  预警类型 ID
     * @param level 预警等级
     *
     * @since 1.2.1
     */
    private static String makeWarningIconUrl(String type, String level) {
        String levelNum = getWarningLevelNum(level);

        // 备注（2022.05.08）：
        // 原定此处根据不同等级返回不同颜色的图片，但由于背景色使用了该颜色，因此此处统一返回白色图标。
        // return HeConstant.WARNING_ICON_BASE_URL + type + "-" + levelNum + ".svg";

        return HeConstant.WARNING_ICON_BASE_URL + type + "-5.svg";
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
     * 查询和风天气城市
     *
     * @param location 需要查询地区的名称，支持文字、以英文逗号分隔的经度,纬度坐标（十进制，最多支持小数点后两位）、LocationID 或 Adcode（仅限中国城市）
     *
     * @since 1.0.0
     */
    public HeCity[] searchCities(String location) {
        HeCityLookupResponse data = heHttpService.searchCities(location);

        String successCode = "200";

        if (!successCode.equals(data.getCode())) {
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
    public WeatherDailyItem[] getWeatherDaily(String location, String days) {
        HeWeatherDailyResponse res = heHttpService.getWeatherDaily(location, days);
        HeWeatherDailyResponse.Daily[] daily = res.getDaily();

        WeatherDailyItem[] list = new WeatherDailyItem[daily.length];
        for (int i = 0; i < daily.length; i++) {
            HeWeatherDailyResponse.Daily source = daily[i];
            WeatherDailyItem target = new WeatherDailyItem();
            BeanUtils.copyProperties(source, target);

            target.setDate(source.getFxDate());
            target.setIconDayUrl(makeIconUrl(source.getIconDay()));
            target.setIconNightUrl(makeIconUrl(source.getIconNight()));
            target.setMoonPhaseIconUrl(makeIconUrl(source.getMoonPhaseIcon()));

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
    public WeatherHourlyItem[] getWeatherHourly(String location, String hours) {
        HeWeatherHourlyResponse res = heHttpService.getWeatherHourly(location, hours);
        HeWeatherHourlyResponse.Hourly[] hourly = res.getHourly();

        WeatherHourlyItem[] list = new WeatherHourlyItem[hourly.length];
        for (int i = 0; i < hourly.length; i++) {
            HeWeatherHourlyResponse.Hourly source = hourly[i];
            WeatherHourlyItem target = new WeatherHourlyItem();
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
        rain.setUpdateTime(res
            .getUpdateTime()
            .substring(11, 16));
        rain.setType(res.getMinutely()[0].getType());

        MinutelyRain.Minutely[] list = new MinutelyRain.Minutely[minutely.length];
        for (int i = 0; i < minutely.length; i++) {
            HeMinutelyResponse.Minutely source = minutely[i];
            MinutelyRain.Minutely target = new MinutelyRain.Minutely();
            target.setTime(source
                .getFxTime()
                .substring(11, 16));
            target.setPrecip(Float.valueOf(source.getPrecip()));

            if (target.getPrecip() > 0) {
                rain.setHasRain(true);
            }

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
     * 获取天气灾害预警
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     *
     * @since 1.2.0
     */
    public WeatherWarningItem[] getWarningNow(String location) {
        HeWarningNowResponse res = heHttpService.getWarningNow(location);
        HeWarningNowResponse.WarningItem[] warningItems = res.getWarning();
        WeatherWarningItem[] list = new WeatherWarningItem[warningItems.length];

        for (int i = 0; i < warningItems.length; i++) {
            HeWarningNowResponse.WarningItem source = warningItems[i];
            WeatherWarningItem target = new WeatherWarningItem();
            BeanUtils.copyProperties(source, target);

            target.setLevelId(getWarningLevelNum(source.getLevel()));
            target.setImageUrl(makeWarningImageUrl(source.getType(), source.getLevel()));
            target.setIconUrl(makeWarningIconUrl(source.getType(), source.getLevel()));

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
    public AirDailyItem[] getAirDaily(String location) {
        HeAirDailyResponse res = heHttpService.getAirDaily(location);
        HeAirDailyResponse.Daily[] daily = res.getDaily();
        AirDailyItem[] list = new AirDailyItem[daily.length];

        for (int i = 0; i < daily.length; i++) {
            HeAirDailyResponse.Daily source = daily[i];
            AirDailyItem target = new AirDailyItem();
            BeanUtils.copyProperties(source, target);
            target.setDate(source.getFxDate());

            list[i] = target;
        }

        return list;
    }

    /**
     * 获取包含空气质量预报的逐天天气预报
     *
     * @param location 需要查询地区的LocationID或以英文逗号分隔的经度,纬度坐标
     *
     * @since 1.2.0
     */
    public WeatherDailyItem[] getWeatherDailyWithAqi(String location, String days) {
        WeatherDailyItem[] weatherDaily = getWeatherDaily(location, days);
        AirDailyItem[] airDaily = getAirDaily(location);

        for (WeatherDailyItem weather : weatherDaily) {
            for (AirDailyItem air : airDaily) {
                if (weather
                    .getDate()
                    .equals(air.getDate())) {
                    weather.setAqiLevel(air.getLevel());
                    weather.setAqiCategory(air.getCategory());
                }
            }
        }

        return weatherDaily;
    }
}
