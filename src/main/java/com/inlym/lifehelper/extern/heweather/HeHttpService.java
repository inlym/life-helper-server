package com.inlym.lifehelper.extern.heweather;

import com.inlym.lifehelper.common.constant.RedisCacheCollector;
import com.inlym.lifehelper.extern.heweather.exception.HeRequestFailedException;
import com.inlym.lifehelper.extern.heweather.pojo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
     * <h2>实测结果说明
     * <li>当有结果时，返回 code=200，且 location 为一个列表。
     * <li>当无结果时，返回 code=404，且无 location 字段。 因此这个方法不抛出错误，在下一个环节处理，返回一个空数组。
     *
     * @param location 需要查询地区的名称，支持文字、以英文逗号分隔的经度,纬度坐标（十进制，最多支持小数点后两位）、LocationID 或 Adcode（仅限中国城市）
     *
     * @since 1.0.0
     */
    @Cacheable(RedisCacheCollector.HE_GEO_CITY_LOOKUP)
    public HeCityLookupResponse getGeoCityLookup(String location) {
        URI uri = UriComponentsBuilder
            .fromHttpUrl("https://geoapi.qweather.com/v2/city/lookup")
            // 只有此处查询城市可能用到中文，因此进行特殊处理，对请求参数进行编码
            .queryParam("location", URLEncoder.encode(location, StandardCharsets.UTF_8))
            .queryParam("key", heProperties.getKey())
            .queryParam("range", "cn")
            .build(true)
            .toUri();

        return restTemplate.getForObject(uri, HeCityLookupResponse.class);
    }

    /**
     * 获取实况天气数据
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     *
     * @see <a href="https://dev.qweather.com/docs/api/weather/weather-now/">官方文档</a>
     * @since 1.0.0
     */
    @Cacheable(RedisCacheCollector.HE_WEATHER_NOW)
    public HeWeatherNowResponse getWeatherNow(String location) {
        String url = UriComponentsBuilder
            .fromHttpUrl("https://api.qweather.com/v7/weather/now")
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
     * 获取逐日天气预报
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     * @param days     天数，支持 `3d`,`7d`,`10d`,`15d`,`30d`
     *
     * @see <a href="https://dev.qweather.com/docs/api/weather/weather-daily-forecast/">官方文档</a>
     * @since 1.0.0
     */
    @Cacheable(RedisCacheCollector.HE_WEATHER_DAILY)
    public HeWeatherDailyResponse getWeatherDaily(String location, String days) {
        String url = UriComponentsBuilder
            .fromHttpUrl("https://api.qweather.com/v7/weather/" + days)
            .queryParam("location", location)
            .queryParam("key", heProperties.getKey())
            .build()
            .toUriString();

        HeWeatherDailyResponse data = restTemplate.getForObject(url, HeWeatherDailyResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            return data;
        }
        throw HeRequestFailedException.create("逐日天气预报", url, data.getCode());
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
            .fromHttpUrl("https://api.qweather.com/v7/weather/" + hours)
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
     * @see <a href="https://dev.qweather.com/docs/api/minutely/minutely-precipitation/">分钟级降水</a>
     * @since 1.0.0
     */
    @Cacheable(RedisCacheCollector.HE_WEATHER_MINUTELY)
    public HeMinutelyResponse getMinutely(String location) {
        String url = UriComponentsBuilder
            .fromHttpUrl("https://api.qweather.com/v7/minutely/5m")
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
     * 获取格点实时天气
     *
     * @param location 需要查询地区的以英文逗号分隔的 经度,纬度 坐标
     *
     * @see <a href="https://dev.qweather.com/docs/api/grid-weather/grid-weather-now/">格点实时天气</a>
     * @since 1.5.0
     */
    @Cacheable(RedisCacheCollector.HE_GRID_WEATHER_NOW)
    public HeGridWeatherNowResponse getGridWeatherNow(String location) {
        String url = UriComponentsBuilder
            .fromHttpUrl("https://api.qweather.com/v7/grid-weather/now")
            .queryParam("location", location)
            .queryParam("key", heProperties.getKey())
            .build()
            .toUriString();

        HeGridWeatherNowResponse data = restTemplate.getForObject(url, HeGridWeatherNowResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            return data;
        }
        throw HeRequestFailedException.create("格点实时天气", url, data.getCode());
    }

    /**
     * 获取格点每日天气预报
     *
     * @param location 需要查询地区的以英文逗号分隔的 经度,纬度 坐标
     * @param days     天数，支持 `3d`,`7d`
     *
     * @see <a href="https://dev.qweather.com/docs/api/weather/weather-daily-forecast/">官方文档</a>
     * @since 1.5.0
     */
    @Cacheable(RedisCacheCollector.HE_GRID_WEATHER_DAILY)
    public HeGridWeatherDailyResponse getGridWeatherDaily(String location, String days) {
        String url = UriComponentsBuilder
            .fromHttpUrl("https://api.qweather.com/v7/grid-weather/" + days)
            .queryParam("location", location)
            .queryParam("key", heProperties.getKey())
            .build()
            .toUriString();

        HeGridWeatherDailyResponse data = restTemplate.getForObject(url, HeGridWeatherDailyResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            return data;
        }
        throw HeRequestFailedException.create("格点每日天气预报", url, data.getCode());
    }

    /**
     * 获取格点逐小时天气预报
     *
     * @param location 需要查询地区的以英文逗号分隔的 经度,纬度 坐标
     * @param hours    小时数，支持 `24h`,`72h`
     *
     * @see <a href="https://dev.qweather.com/docs/api/grid-weather/grid-weather-hourly-forecast/">官方文档</a>
     * @since 1.5.0
     */
    @Cacheable(RedisCacheCollector.HE_GRID_WEATHER_HOURLY)
    public HeGridWeatherHourlyResponse getGridWeatherHourly(String location, String hours) {
        String url = UriComponentsBuilder
            .fromHttpUrl("https://api.qweather.com/v7/grid-weather/" + hours)
            .queryParam("location", location)
            .queryParam("key", heProperties.getKey())
            .build()
            .toUriString();

        HeGridWeatherHourlyResponse data = restTemplate.getForObject(url, HeGridWeatherHourlyResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            return data;
        }
        throw HeRequestFailedException.create("格点逐小时天气预报", url, data.getCode());
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
            .fromHttpUrl("https://api.qweather.com/v7/warning/now")
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
     * 获取天气预警城市列表
     *
     * @see <a href="https://dev.qweather.com/docs/api/warning/weather-warning-city-list/">官方文档</a>
     * @since 1.5.0
     */
    @Cacheable(RedisCacheCollector.HE_WARNING_LIST)
    public HeWarningListResponse getWarningList() {
        String url = UriComponentsBuilder
            .fromHttpUrl("https://api.qweather.com/v7/warning/list")
            .queryParam("range", "cn")
            .queryParam("key", heProperties.getKey())
            .build()
            .toUriString();

        HeWarningListResponse data = restTemplate.getForObject(url, HeWarningListResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            return data;
        }
        throw HeRequestFailedException.create("天气预警城市列表", url, data.getCode());
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
            .fromHttpUrl("https://api.qweather.com/v7/indices/" + days)
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
     * 获取实时空气质量
     *
     * @param location 需要查询地区的LocationID或以英文逗号分隔的经度,纬度坐标
     *
     * @see <a href="https://dev.qweather.com/docs/api/air/air-now/">官方文档</a>
     * @since 1.0.0
     */
    @Cacheable(RedisCacheCollector.HE_WEATHER_AIR_NOW)
    public HeAirNowResponse getAirNow(String location) {
        String url = UriComponentsBuilder
            .fromHttpUrl("https://api.qweather.com/v7/air/now")
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
    @Cacheable(RedisCacheCollector.HE_WEATHER_AIR_DAILY)
    public HeAirDailyResponse getAirDaily(String location) {
        String url = UriComponentsBuilder
            .fromHttpUrl("https://api.qweather.com/v7/air/5d")
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
