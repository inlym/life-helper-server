package com.inlym.lifehelper.external.heweather;

import com.inlym.lifehelper.common.constant.RedisCacheCollector;
import com.inlym.lifehelper.external.heweather.exception.HeRequestFailedException;
import com.inlym.lifehelper.external.heweather.pojo.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 和风天气 HTTP 请求服务类
 *
 * <h2>主要用途
 * <p>将对和风天气 API 的 HTTP 请求封装为内部可直接调用的方法。
 *
 * <h2>注意事项
 * <li>仅封装 HTTP 请求，不对响应数据做任何数据处理。
 * <li>方法入参一般即为发起 HTTP 请求所需的参数，对部分未用到的参数不做封装。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-17
 * @since 1.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class HeHttpService {
    /** 表示请求成功的 `code` 值 */
    public static final String SUCCESS_CODE = "200";

    /** API 请求地址前缀 */
    public static final String BASE_URL = "https://api.qweather.com/v7";

    private final RestTemplate restTemplate;

    private final HeProperties heProperties;

    /**
     * 城市信息查询
     *
     * <h2>说明
     * <p>实测结果：
     * <li>当有结果时，返回 code=200，且 location 为一个列表。
     * <li>当无结果时，返回 code=404，且无 location 字段。 因此这个方法不抛出错误，在下一个环节处理，返回一个空数组。
     *
     * @param location 需要查询地区的名称，支持文字、以英文逗号分隔的经度,纬度坐标（十进制，最多支持小数点后两位）、LocationID 或 Adcode（仅限中国城市）
     *
     * @since 1.0.0
     */
    @SneakyThrows
    @Cacheable(RedisCacheCollector.HE_SEARCH_CITIES)
    public HeCityLookupResponse searchCities(String location) {
        String url = UriComponentsBuilder
            .fromHttpUrl("https://geoapi.qweather.com/v2/city/lookup")
            .queryParam("location", location)
            .queryParam("key", heProperties.getKey())
            .queryParam("range", "cn")
            .toUriString();

        // 在 `url` 外面套一层 `new URI()` 的原因是：
        // 这样可以避免请求参数中包含中文时，被默认规则转码变成乱码的问题。
        return restTemplate.getForObject(new URI(url), HeCityLookupResponse.class);
    }

    /**
     * 获取实时天气
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     *
     * @see <a href="https://dev.qweather.com/docs/api/weather/weather-now/">官方文档</a>
     * @since 1.0.0
     */
    @Cacheable(RedisCacheCollector.HE_WEATHER_NOW)
    public HeWeatherNowResponse getWeatherNow(String location) {
        String url = UriComponentsBuilder
            .fromHttpUrl(BASE_URL + "/weather/now")
            .queryParam("location", location)
            .queryParam("key", heProperties.getKey())
            .build()
            .toUriString();

        HeWeatherNowResponse data = restTemplate.getForObject(url, HeWeatherNowResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            return data;
        }
        throw HeRequestFailedException.create("实时天气", url, data.getCode());
    }

    /**
     * 获取逐天天气预报
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     * @param days     天数，支持 `3d`,`7d`,`10d`,`15d`
     *
     * @see <a href="https://dev.qweather.com/docs/api/weather/weather-daily-forecast/">官方文档</a>
     * @since 1.0.0
     */
    @Cacheable(RedisCacheCollector.HE_WEATHER_DAILY)
    public HeWeatherDailyResponse getWeatherDaily(String location, String days) {
        String url = UriComponentsBuilder
            .fromHttpUrl(BASE_URL + "/weather/" + days)
            .queryParam("location", location)
            .queryParam("key", heProperties.getKey())
            .build()
            .toUriString();

        HeWeatherDailyResponse data = restTemplate.getForObject(url, HeWeatherDailyResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            return data;
        }
        throw HeRequestFailedException.create("逐天天气预报", url, data.getCode());
    }

    /**
     * 获取逐小时天气预报
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     * @param hours    小时数，支持 `24h`,`72h`,`168h`
     *
     * @see <a href="https://dev.qweather.com/docs/api/weather/weather-hourly-forecast/">官方文档</a>
     * @since 1.0.0
     */
    @Cacheable(RedisCacheCollector.HE_WEATHER_HOURLY)
    public HeWeatherHourlyResponse getWeatherHourly(String location, String hours) {
        String url = UriComponentsBuilder
            .fromHttpUrl(BASE_URL + "/weather/" + hours)
            .queryParam("location", location)
            .queryParam("key", heProperties.getKey())
            .build()
            .toUriString();

        HeWeatherHourlyResponse data = restTemplate.getForObject(url, HeWeatherHourlyResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            return data;
        }
        throw HeRequestFailedException.create("逐小时天气预报", url, data.getCode());
    }

    /**
     * 获取分钟级降水
     *
     * @param location 需要查询地区的以英文逗号分隔的经度,纬度坐标（十进制，最多支持小数点后两位）
     *
     * @see <a href="https://dev.qweather.com/docs/api/grid-weather/minutely/">官方文档</a>
     * @since 1.0.0
     */
    @Cacheable(RedisCacheCollector.HE_MINUTELY)
    public HeMinutelyResponse getMinutely(String location) {
        String url = UriComponentsBuilder
            .fromHttpUrl(BASE_URL + "/minutely/5m")
            .queryParam("location", location)
            .queryParam("key", heProperties.getKey())
            .build()
            .toUriString();

        HeMinutelyResponse data = restTemplate.getForObject(url, HeMinutelyResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            return data;
        }
        throw HeRequestFailedException.create("分钟级降水", url, data.getCode());
    }

    /**
     * 获取天气生活指数
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     * @param days     小时数，支持 `1d`,`3d`
     *
     * @see <a href="https://dev.qweather.com/docs/api/indices/">官方文档</a>
     * @since 1.0.0
     */
    @Cacheable(RedisCacheCollector.HE_INDICES_DAILY)
    public HeIndicesResponse getIndicesDaily(String location, String days) {
        String url = UriComponentsBuilder
            .fromHttpUrl(BASE_URL + "/indices/" + days)
            .queryParam("location", location)
            .queryParam("key", heProperties.getKey())
            .queryParam("type", "0")
            .build()
            .toUriString();

        HeIndicesResponse data = restTemplate.getForObject(url, HeIndicesResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            return data;
        }
        throw HeRequestFailedException.create("天气生活指数", url, data.getCode());
    }

    /**
     * 获取天气灾害预警
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     *
     * @see <a href="https://dev.qweather.com/docs/api/warning/weather-warning/">官方文档</a>
     * @since 1.2.0
     */
    @Cacheable(RedisCacheCollector.HE_WARNING_NOW)
    public HeWarningNowResponse getWarningNow(String location) {
        String url = UriComponentsBuilder
            .fromHttpUrl(BASE_URL + "/warning/now")
            .queryParam("location", location)
            .queryParam("key", heProperties.getKey())
            .build()
            .toUriString();

        HeWarningNowResponse data = restTemplate.getForObject(url, HeWarningNowResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            return data;
        }
        throw HeRequestFailedException.create("天气灾害预警", url, data.getCode());
    }

    /**
     * 获取实时空气质量
     *
     * @param location 需要查询地区的LocationID或以英文逗号分隔的经度,纬度坐标
     *
     * @see <a href="https://dev.qweather.com/docs/api/air/air-now/">官方文档</a>
     * @since 1.0.0
     */
    @Cacheable(RedisCacheCollector.HE_AIR_NOW)
    public HeAirNowResponse getAirNow(String location) {
        String url = UriComponentsBuilder
            .fromHttpUrl(BASE_URL + "/air/now")
            .queryParam("location", location)
            .queryParam("key", heProperties.getKey())
            .build()
            .toUriString();

        HeAirNowResponse data = restTemplate.getForObject(url, HeAirNowResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            return data;
        }
        throw HeRequestFailedException.create("实时空气质量", url, data.getCode());
    }

    /**
     * 获取空气质量预报
     *
     * @param location 需要查询地区的LocationID或以英文逗号分隔的经度,纬度坐标
     *
     * @see <a href="https://dev.qweather.com/docs/api/air/air-daily-forecast/">官方文档</a>
     * @since 1.0.0
     */
    @Cacheable(RedisCacheCollector.HE_AIR_DAILY)
    public HeAirDailyResponse getAirDaily(String location) {
        String url = UriComponentsBuilder
            .fromHttpUrl(BASE_URL + "/air/5d")
            .queryParam("location", location)
            .queryParam("key", heProperties.getKey())
            .build()
            .toUriString();

        HeAirDailyResponse data = restTemplate.getForObject(url, HeAirDailyResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            return data;
        }
        throw HeRequestFailedException.create("空气质量预报", url, data.getCode());
    }
}
