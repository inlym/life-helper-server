package com.inlym.lifehelper.external.heweather;

import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.external.heweather.pojo.HeWeatherNowResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 和风天气 HTTP 请求服务类
 * <p>
 * [主要用途]
 * 将对和风天气 API 的 HTTP 请求封装为内部可直接调用的方法。
 * <p>
 * [注意事项]
 * 1. 仅封装 HTTP 请求，不对响应数据做任何数据处理。
 * 2. 方法入参一般即为发起 HTTP 请求所需的参数，对部分未用到的参数不做封装。
 *
 * @author inlym
 * @date 2022-02-17
 **/
@Service
@Slf4j
public class HeWeatherHttpService {
    /** 表示请求成功的 `code` 值 */
    public static final String SUCCESS_CODE = "200";

    private final RestTemplate restTemplate = new RestTemplate();

    private final HeWeatherProperties heWeatherProperties;

    public HeWeatherHttpService(HeWeatherProperties heWeatherProperties) {
        this.heWeatherProperties = heWeatherProperties;
    }

    /**
     * 获取实时天气
     *
     * @param location 需要查询地区的 LocationID 或以英文逗号分隔的经度,纬度坐标
     *
     * @see <a href="https://dev.qweather.com/docs/api/weather/weather-now/">实时天气</a>
     */
    @SneakyThrows
    public HeWeatherNowResponse getWeatherNow(String location) {
        // 不含参数的请求地址前缀
        String baseUrl = "https://devapi.qweather.com/v7/weather/now";

        // 包含请求参数的完整请求地址
        String url = UriComponentsBuilder
            .fromHttpUrl(baseUrl)
            .queryParam("location", location)
            .queryParam("key", heWeatherProperties.getDevKey())
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
}
