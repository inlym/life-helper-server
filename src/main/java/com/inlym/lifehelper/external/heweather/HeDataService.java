package com.inlym.lifehelper.external.heweather;

import com.inlym.lifehelper.external.heweather.pojo.*;
import com.inlym.lifehelper.weather.data.pojo.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 和风天气数据服务
 *
 * <h2>主要用途
 * <p>将从和风天气 API 通过 HTTP 请求获取的数据转化为内部使用的数据格式。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/3/26
 * @since 1.0.0
 **/
@Service
@RequiredArgsConstructor
public final class HeDataService {
    private final HeHttpService heHttpService;

    /**
     * 查找和风天气城市列表
     *
     * @param location 需要查询地区的名称，支持文字、以英文逗号分隔的经度,纬度坐标（十进制，最多支持小数点后两位）、LocationID 或 Adcode（仅限中国城市）
     *
     * @since 1.5.0
     */
    public List<String> getGeoLocations(String location) {
        List<String> locations = new ArrayList<>();
        HeCityLookupResponse data = heHttpService.getGeoCityLookup(location);

        if (HeHttpService.SUCCESS_CODE.equals(data.getCode()) && data.getLocation() != null) {
            for (HeCityLookupResponse.City city : data.getLocation()) {
                locations.add(city.getId());
            }
        }

        return locations;
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
        HeWeatherNowResponse.Now source = res.getNow();

        return WeatherNow
            .builder()
            .iconUrl(HeUtils.getIconUrl(source.getIcon()))
            .type(HeUtils.getWeatherType(source.getIcon()))
            .updateTime(HeUtils.parseTime(res.getUpdateTime()))
            .temp(source.getTemp())
            .text(source.getText())
            .wind360(source.getWind360())
            .windDir(source.getWindDir())
            .windScale(source.getWindScale())
            .windSpeed(source.getWindSpeed())
            .humidity(source.getHumidity())
            .pressure(source.getPressure())
            .vis(source.getVis())
            .build();
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
    public WeatherDailyItem[] getWeatherDaily(String location, String days) {
        HeWeatherDailyResponse res = heHttpService.getWeatherDaily(location, days);
        HeWeatherDailyResponse.Daily[] daily = res.getDaily();

        WeatherDailyItem[] list = new WeatherDailyItem[daily.length];
        for (int i = 0; i < daily.length; i++) {
            HeWeatherDailyResponse.Daily source = daily[i];
            WeatherDailyItem target = new WeatherDailyItem();
            BeanUtils.copyProperties(source, target);

            target.setDate(source.getFxDate());
            target.setIconDayUrl(HeUtils.getIconUrl(source.getIconDay()));
            target.setIconNightUrl(HeUtils.getIconUrl(source.getIconNight()));
            target.setMoonPhaseIconUrl(HeUtils.getIconUrl(source.getMoonPhaseIcon()));

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

            target.setIconUrl(HeUtils.getIconUrl(source.getIcon()));

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
     * 获取格点实时天气
     *
     * @param location 需要查询地区的以英文逗号分隔的 经度,纬度 坐标
     *
     * @see <a href="https://dev.qweather.com/docs/api/grid-weather/grid-weather-now/">格点实时天气</a>
     * @since 1.5.0
     */
    public GridWeatherNow getGridWeatherNow(String location) {
        HeGridWeatherNowResponse res = heHttpService.getGridWeatherNow(location);
        HeGridWeatherNowResponse.Now now = res.getNow();

        return GridWeatherNow
            .builder()
            .iconUrl(HeUtils.getIconUrl(now.getIcon()))
            .type(HeUtils.getWeatherType(now.getIcon()))
            .updateTime(HeUtils.parseTime(res.getUpdateTime()))
            .temp(now.getTemp())
            .text(now.getText())
            .wind360(now.getWind360())
            .windDir(now.getWindDir())
            .windScale(now.getWindScale())
            .windSpeed(now.getWindSpeed())
            .humidity(now.getHumidity())
            .pressure(now.getPressure())
            .build();
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
            target.setImageUrl(HeUtils.getLiveImageUrl(target.getType()));

            list[i] = target;
        }

        return list;
    }

    /**
     * 获取天气灾害预警列表
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     *
     * @since 1.5.0
     */
    public List<WarningNow> getWarningNow(String location) {
        HeWarningNowResponse res = heHttpService.getWarningNow(location);
        HeWarningNowResponse.WarningItem[] warningItems = res.getWarning();
        List<WarningNow> list = new ArrayList<>();

        LocalDateTime updateTime = HeUtils.parseTime(res.getUpdateTime());

        for (HeWarningNowResponse.WarningItem item : warningItems) {
            WarningNow warningNow = WarningNow
                .builder()
                .imageUrl(HeUtils.getWarningImageUrl(item.getType(), item.getSeverityColor()))
                .updateTime(updateTime)
                .id(item.getId())
                .pubTime(HeUtils.parseTime(item.getPubTime()))
                .title(item.getTitle())
                .status(item.getStatus())
                .type(item.getType())
                .typeName(item.getTypeName())
                .text(item.getText())
                .build();

            list.add(warningNow);
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
