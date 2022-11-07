package com.inlym.lifehelper.external.heweather;

import com.inlym.lifehelper.external.heweather.pojo.*;
import com.inlym.lifehelper.weather.data.pojo.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
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
     * @since 1.0.0
     */
    public WeatherNow getWeatherNow(String location) {
        HeWeatherNowResponse res = heHttpService.getWeatherNow(location);
        HeWeatherNowResponse.Now source = res.getNow();

        Wind wind = Wind
            .builder()
            .angle(source.getWind360())
            .direction(source.getWindDir())
            .scale(source.getWindScale())
            .speed(source.getWindSpeed())
            .build();

        return WeatherNow
            .builder()
            .updateTime(HeUtils.parseTime(res.getUpdateTime()))
            .temp(source.getTemp())
            .text(source.getText())
            .iconUrl(HeUtils.getIconUrl(source.getIcon()))
            .type(HeUtils.getWeatherType(source.getIcon()))
            .wind(wind)
            .humidity(source.getHumidity())
            .pressure(source.getPressure())
            .vis(source.getVis())
            .build();
    }

    /**
     * 获取逐天天气预报
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     * @param days     天数，支持 `3d`,`7d`,`10d`,`15d`,`30d`
     *
     * @see HeHttpService#getWeatherDaily
     * @since 1.0.0
     */
    public List<WeatherDaily> getWeatherDaily(String location, String days) {
        List<WeatherDaily> list = new ArrayList<>();

        HeWeatherDailyResponse res = heHttpService.getWeatherDaily(location, days);

        for (HeWeatherDailyResponse.Daily source : res.getDaily()) {
            WeatherDaily target = new WeatherDaily();
            BeanUtils.copyProperties(source, target);

            Star sun = Star
                .builder()
                .riseTime(LocalTime.parse(source.getSunrise()))
                .setTime(LocalTime.parse(source.getSunset()))
                .build();

            Star moon = Star
                .builder()
                .riseTime(LocalTime.parse(source.getMoonrise()))
                .setTime(LocalTime.parse(source.getMoonset()))
                .build();

            Wind windDay = Wind
                .builder()
                .angle(source.getWind360Day())
                .direction(source.getWindDirDay())
                .scale(source.getWindScaleDay())
                .speed(source.getWindSpeedDay())
                .build();

            Wind windNight = Wind
                .builder()
                .angle(source.getWind360Night())
                .direction(source.getWindDirNight())
                .scale(source.getWindScaleNight())
                .speed(source.getWindSpeedNight())
                .build();

            WeatherDaily.HalfDay day = WeatherDaily.HalfDay
                .builder()
                .temp(source.getTempMax())
                .text(source.getTextDay())
                .iconUrl(HeUtils.getIconUrl(source.getIconDay()))
                .wind(windDay)
                .build();

            WeatherDaily.HalfDay night = WeatherDaily.HalfDay
                .builder()
                .temp(source.getTempMin())
                .text(source.getTextNight())
                .iconUrl(HeUtils.getIconUrl(source.getIconNight()))
                .wind(windNight)
                .build();

            target.setSun(sun);
            target.setMoon(moon);
            target.setDay(day);
            target.setNight(night);
            target.setDate(LocalDate.parse(source.getFxDate()));
            target.setSimpleDate(LocalDate.parse(source.getFxDate()));
            target.setMoonPhaseIconUrl(HeUtils.getIconUrl(source.getMoonPhaseIcon()));

            // 天气描述
            if (source
                .getTextDay()
                .equals(source.getTextNight())) {
                target.setText(source.getTextDay());
            } else {
                target.setText(source.getTextDay() + "转" + source.getTextNight());
            }

            list.add(target);
        }

        return list;
    }

    /**
     * 获取逐小时天气预报
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     * @param hours    小时数，支持 `24h`,`72h`,`168h`
     *
     * @since 1.0.0
     */
    @SneakyThrows
    public List<WeatherHourly> getWeatherHourly(String location, String hours) {
        List<WeatherHourly> list = new ArrayList<>();

        HeWeatherHourlyResponse res = heHttpService.getWeatherHourly(location, hours);
        for (HeWeatherHourlyResponse.Hourly source : res.getHourly()) {
            Wind wind = Wind
                .builder()
                .angle(source.getWind360())
                .direction(source.getWindDir())
                .scale(source.getWindScale())
                .speed(source.getWindSpeed())
                .build();

            list.add(WeatherHourly
                .builder()
                .time(HeUtils.parseTime(source.getFxTime()))
                .iconUrl(HeUtils.getIconUrl(source.getIcon()))
                .wind(wind)
                .temp(source.getTemp())
                .text(source.getText())
                .humidity(source.getHumidity())
                .precip(source.getPrecip())
                .pop(source.getPop())
                .pressure(source.getPressure())
                .build());
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

        MinutelyRain rain = new MinutelyRain();
        rain.setSummary(res.getSummary());
        rain.setUpdateTime(HeUtils.parseTime(res.getUpdateTime()));
        rain.setType(res.getMinutely()[0].getType());

        List<MinutelyRain.Minutely> list = new ArrayList<>();
        for (HeMinutelyResponse.Minutely source : res.getMinutely()) {
            float precip = Float.parseFloat(source.getPrecip());
            if (precip > 0) {
                rain.setHasRain(true);
            }

            list.add(MinutelyRain.Minutely
                .builder()
                .time(HeUtils.parseTime(source.getFxTime()))
                .precip(precip)
                .build());
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

        Wind wind = Wind
            .builder()
            .angle(now.getWind360())
            .direction(now.getWindDir())
            .scale(now.getWindScale())
            .speed(now.getWindSpeed())
            .build();

        return GridWeatherNow
            .builder()
            .iconUrl(HeUtils.getIconUrl(now.getIcon()))
            .type(HeUtils.getWeatherType(now.getIcon()))
            .updateTime(HeUtils.parseTime(res.getUpdateTime()))
            .temp(now.getTemp())
            .text(now.getText())
            .wind(wind)
            .humidity(now.getHumidity())
            .pressure(now.getPressure())
            .build();
    }

    /**
     * 获取天气灾害预警列表
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     *
     * @since 1.5.0
     */
    public List<WarningNow> getWarningNow(String location) {
        List<WarningNow> list = new ArrayList<>();

        HeWarningNowResponse res = heHttpService.getWarningNow(location);
        for (HeWarningNowResponse.WarningItem item : res.getWarning()) {
            WarningNow warningNow = WarningNow
                .builder()
                .name(item.getTypeName() + HeUtils.translateWarningColor(item.getSeverityColor()) + "预警")
                .imageUrl(HeUtils.getWarningImageUrl(item.getType(), item.getSeverityColor()))
                .id(item.getId())
                .pubTime(HeUtils.parseTime(item.getPubTime()))
                .title(item.getTitle())
                .type(item.getType())
                .typeName(item.getTypeName())
                .text(item.getText())
                .build();

            list.add(warningNow);
        }

        return list;
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
        List<String> list = new ArrayList<>();

        HeWarningListResponse res = heHttpService.getWarningList();
        for (HeWarningListResponse.WarningLocation location : res.getWarningLocList()) {
            list.add(location.getLocationId());
        }

        return list;
    }

    /**
     * 获取天气生活指数
     *
     * <h2>说明
     * <p>目前只需要4项，分别为：运动（1），洗车（2），穿衣（3），旅游（6）
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     * @param days     小时数，支持 `1d`,`3d`
     *
     * @since 1.0.0
     */
    public List<LivingIndex> getIndicesDaily(String location, String days) {
        List<String> types = new ArrayList<>();
        types.add("1");
        types.add("2");
        types.add("3");
        types.add("6");

        List<LivingIndex> list = new ArrayList<>();
        for (String type : types) {
            list.add(LivingIndex
                .builder()
                .imageUrl(HeUtils.getLiveImageUrl(type))
                .type(type)
                .daily(new ArrayList<>())
                .build());
        }

        HeIndicesResponse res = heHttpService.getIndicesDaily(location, days);
        for (HeIndicesResponse.Daily daily : res.getDaily()) {
            for (LivingIndex item : list) {
                if (daily
                    .getType()
                    .equals(item.getType())) {
                    // 名称为空则设置上
                    if (item.getName() == null) {
                        item.setName(daily.getName());
                    }

                    item
                        .getDaily()
                        .add(LivingIndex.DailyIndex
                            .builder()
                            .date(LocalDate.parse(daily.getDate()))
                            .level(daily.getLevel())
                            .category(daily.getCategory())
                            .text(daily.getText())
                            .build());
                }
            }
        }

        // 对列表中中的子列表再一次重排序
        for (LivingIndex item : list) {
            item
                .getDaily()
                .sort(Comparator.comparing(LivingIndex.DailyIndex::getDate));
        }

        return list;
    }

    /**
     * 获取实时空气质量
     *
     * @param location 需要查询地区的LocationID或以英文逗号分隔的经度,纬度坐标
     *
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
     * @since 1.0.0
     */
    public List<AirDaily> getAirDaily(String location) {
        List<AirDaily> list = new ArrayList<>();

        HeAirDailyResponse res = heHttpService.getAirDaily(location);
        for (HeAirDailyResponse.Daily source : res.getDaily()) {
            list.add(AirDaily
                .builder()
                .date(LocalDate.parse(source.getFxDate()))
                .aqi(source.getAqi())
                .level(source.getLevel())
                .category(source.getCategory())
                .primary(source.getPrimary())
                .build());
        }

        return list;
    }
}
