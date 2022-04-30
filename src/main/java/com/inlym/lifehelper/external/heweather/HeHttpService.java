package com.inlym.lifehelper.external.heweather;

import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.external.heweather.pojo.*;
import lombok.AllArgsConstructor;
import lombok.Data;
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
public class HeHttpService {
    /** 表示请求成功的 `code` 值 */
    public static final String SUCCESS_CODE = HeConstant.SUCCESS_CODE;

    private final RestTemplate restTemplate = new RestTemplate();

    private final HeProperties heProperties;

    /** 开发版配置信息 */
    private final Config devConfig;

    /** 商业版配置信息 */
    private final Config proConfig;

    public HeHttpService(HeProperties heProperties) {
        this.heProperties = heProperties;

        this.devConfig = new Config(HeConstant.DEV_API_BASE_URL, heProperties.getDevKey());
        this.proConfig = new Config(HeConstant.PRO_API_BASE_URL, heProperties.getProKey());
    }

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
    @Cacheable("hefeng:city")
    public HeCityLookupResponse searchCities(String location) {
        String url = UriComponentsBuilder
            .fromHttpUrl("https://geoapi.qweather.com/v2/city/lookup")
            .queryParam("location", location)
            .queryParam("key", heProperties.getDevKey())
            .queryParam("range", "cn")
            .queryParam("gzip", "n")
            .toUriString();

        // 在 `url` 外面套一层 `new URI()` 的原因是：
        // 这样可以避免请求参数中包含中文时，被默认规则转码变成乱码的问题。
        HeCityLookupResponse data = restTemplate.getForObject(new URI(url), HeCityLookupResponse.class);

        assert data != null;
        log.info("[HTTP] [城市信息查询] code={}, url={}", data.getCode(), url);

        return data;
    }

    /**
     * 获取实时天气
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     *
     * @see <a href="https://dev.qweather.com/docs/api/weather/weather-now/">官方文档</a>
     * @since 1.0.0
     */
    @SneakyThrows
    @Cacheable("hefeng:now")
    public HeWeatherNowResponse getWeatherNow(String location) {
        String path = "/weather/now";

        // 包含请求参数的完整请求地址
        String url = UriComponentsBuilder
            .fromHttpUrl(devConfig.getBaseUrl() + path)
            .queryParam("location", location)
            .queryParam("key", heProperties.getDevKey())
            .queryParam("gzip", "n")
            .build()
            .toUriString();

        HeWeatherNowResponse data = restTemplate.getForObject(url, HeWeatherNowResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            log.info("[HTTP] [实时天气] url={}", url);
            return data;
        }
        throw new ExternalHttpRequestException("实时天气", url, data.getCode());
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
    @SneakyThrows
    @Cacheable("hefeng:daily")
    public HeWeatherDailyResponse getWeatherDaily(String location, String days) {
        Config config = (HeConstant.WeatherDailyDays.DAYS_15.equals(days) || HeConstant.WeatherDailyDays.DAYS_10.equals(days)) ? proConfig : devConfig;
        String path = "/weather/" + days;

        // 包含请求参数的完整请求地址
        String url = UriComponentsBuilder
            .fromHttpUrl(config.getBaseUrl() + path)
            .queryParam("location", location)
            .queryParam("key", config.getKey())
            .queryParam("gzip", "n")
            .build()
            .toUriString();

        HeWeatherDailyResponse data = restTemplate.getForObject(url, HeWeatherDailyResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            log.info("[HTTP] [逐天天气预报] url={}", url);
            return data;
        }
        throw new ExternalHttpRequestException("逐天天气预报", url, data.getCode());
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
    @SneakyThrows
    @Cacheable("hefeng:hourly")
    public HeWeatherHourlyResponse getWeatherHourly(String location, String hours) {
        Config config = HeConstant.WeatherHourlyHours.HOURS_24.equals(hours) ? devConfig : proConfig;
        String path = "/weather/" + hours;

        // 包含请求参数的完整请求地址
        String url = UriComponentsBuilder
            .fromHttpUrl(config.getBaseUrl() + path)
            .queryParam("location", location)
            .queryParam("key", config.getKey())
            .queryParam("gzip", "n")
            .build()
            .toUriString();

        HeWeatherHourlyResponse data = restTemplate.getForObject(url, HeWeatherHourlyResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            log.info("[HTTP] [逐小时天气预报] url={}", url);
            return data;
        }
        throw new ExternalHttpRequestException("逐小时天气预报", url, data.getCode());
    }

    /**
     * 获取分钟级降水
     *
     * @param location 需要查询地区的以英文逗号分隔的经度,纬度坐标（十进制，最多支持小数点后两位）
     *
     * @see <a href="https://dev.qweather.com/docs/api/grid-weather/minutely/">官方文档</a>
     * @since 1.0.0
     */
    @SneakyThrows
    @Cacheable("hefeng:minutely")
    public HeMinutelyResponse getMinutely(String location) {
        String path = "/minutely/5m";

        // 包含请求参数的完整请求地址
        String url = UriComponentsBuilder
            .fromHttpUrl(proConfig.getBaseUrl() + path)
            .queryParam("location", location)
            .queryParam("key", proConfig.getKey())
            .queryParam("gzip", "n")
            .build()
            .toUriString();

        HeMinutelyResponse data = restTemplate.getForObject(url, HeMinutelyResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            log.info("[HTTP] [分钟级降水] url={}", url);
            return data;
        }
        throw new ExternalHttpRequestException("分钟级降水", url, data.getCode());
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
    @SneakyThrows
    @Cacheable("hefeng:indices")
    public HeIndicesResponse getIndicesDaily(String location, String days) {
        Config config = "1d".equals(days) ? devConfig : proConfig;
        String path = "/indices/" + days;

        // 包含请求参数的完整请求地址
        String url = UriComponentsBuilder
            .fromHttpUrl(config.getBaseUrl() + path)
            .queryParam("location", location)
            .queryParam("key", config.getKey())
            .queryParam("type", "0")
            .queryParam("gzip", "n")
            .build()
            .toUriString();

        HeIndicesResponse data = restTemplate.getForObject(url, HeIndicesResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            log.info("[HTTP] [天气生活指数] url={}", url);
            return data;
        }
        throw new ExternalHttpRequestException("天气生活指数", url, data.getCode());
    }

    /**
     * 获取天气灾害预警
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     *
     * @see <a href="https://dev.qweather.com/docs/api/warning/weather-warning/">官方文档</a>
     * @since 1.2.0
     */
    @SneakyThrows
    @Cacheable("hefeng:warning-now")
    public HeWarningNowResponse getWarningNow(String location) {
        String path = "/warning/now";

        // 包含请求参数的完整请求地址
        String url = UriComponentsBuilder
            .fromHttpUrl(devConfig.getBaseUrl() + path)
            .queryParam("location", location)
            .queryParam("key", heProperties.getDevKey())
            .queryParam("gzip", "n")
            .build()
            .toUriString();

        HeWarningNowResponse data = restTemplate.getForObject(url, HeWarningNowResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            log.info("[HTTP] [天气灾害预警] url={}", url);
            return data;
        }
        throw new ExternalHttpRequestException("天气灾害预警", url, data.getCode());
    }

    /**
     * 获取实时空气质量
     *
     * @param location 需要查询地区的LocationID或以英文逗号分隔的经度,纬度坐标
     *
     * @see <a href="https://dev.qweather.com/docs/api/air/air-now/">官方文档</a>
     * @since 1.0.0
     */
    @SneakyThrows
    @Cacheable("hefeng:air-now")
    public HeAirNowResponse getAirNow(String location) {
        String path = "/air/now";

        // 包含请求参数的完整请求地址
        String url = UriComponentsBuilder
            .fromHttpUrl(devConfig.getBaseUrl() + path)
            .queryParam("location", location)
            .queryParam("key", devConfig.getKey())
            .queryParam("gzip", "n")
            .build()
            .toUriString();

        HeAirNowResponse data = restTemplate.getForObject(url, HeAirNowResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            log.info("[HTTP] [实时空气质量] url={}", url);
            return data;
        }
        throw new ExternalHttpRequestException("实时空气质量", url, data.getCode());
    }

    /**
     * 获取空气质量预报
     *
     * @param location 需要查询地区的LocationID或以英文逗号分隔的经度,纬度坐标
     *
     * @see <a href="https://dev.qweather.com/docs/api/air/air-daily-forecast/">官方文档</a>
     * @since 1.0.0
     */
    @SneakyThrows
    @Cacheable("hefeng:air-daily")
    public HeAirDailyResponse getAirDaily(String location) {
        String path = "/air/5d";

        // 包含请求参数的完整请求地址
        String url = UriComponentsBuilder
            .fromHttpUrl(proConfig.getBaseUrl() + path)
            .queryParam("location", location)
            .queryParam("key", proConfig.getKey())
            .queryParam("gzip", "n")
            .build()
            .toUriString();

        HeAirDailyResponse data = restTemplate.getForObject(url, HeAirDailyResponse.class);

        assert data != null;
        if (SUCCESS_CODE.equals(data.getCode())) {
            log.info("[HTTP] [空气质量预报] url={}", url);
            return data;
        }
        throw new ExternalHttpRequestException("空气质量预报", url, data.getCode());
    }

    /**
     * 和风天气配置信息类
     *
     * <h2>主要用途
     * <p>和风天气开发者密钥分开发版和商业版，两个密钥对应的请求地址，这个类将这两个信息封装起来。
     */
    @Data
    @AllArgsConstructor
    private static class Config {
        /** 请求地址前缀部分 */
        private String baseUrl;

        /** 密钥 */
        private String key;
    }
}
