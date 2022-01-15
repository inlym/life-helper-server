package com.inlym.lifehelper.external.hefeng;

import com.inlym.lifehelper.external.hefeng.model.HefengLookUpCityResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class HefengHttpService {
    private final Log logger = LogFactory.getLog(getClass());

    private final RestTemplate restTemplate;

    private final HefengProperties hefengProperties;

    public HefengHttpService(HefengProperties hefengProperties) {
        this.hefengProperties = hefengProperties;

        // 和风天气响应会使用 gzip 压缩，使用这个 HTTP 客户端自动处理解压缩
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
            HttpClientBuilder
                .create()
                .build());
        this.restTemplate = new RestTemplate(clientHttpRequestFactory);
    }

    /**
     * 城市信息查询
     *
     * @param location 需要查询地区的名称
     *
     * @see <a href="https://dev.qweather.com/docs/api/geo/city-lookup/">城市信息查询</a>
     */
    public HefengLookUpCityResponse lockUpCity(String location) throws Exception {
        // 不含参数的请求地址前缀
        String baseURL = "https://geoapi.qweather.com/v2/city/lookup";

        // 包含请求参数的完整请求地址
        String url = UriComponentsBuilder
            .fromHttpUrl(baseURL)
            .queryParam("location", location)
            .queryParam("key", hefengProperties.getDevKey())
            .build()
            .toUriString();
        logger.debug(url);

        HefengLookUpCityResponse data = restTemplate.getForObject(url, HefengLookUpCityResponse.class);

        if (data != null && data
            .getCode()
            .equals("200")) {
            logger.debug("[城市信息查询] location=" + location + ", data=" + data);

            return data;
        }

        throw new Exception("[城市信息查询] 请求错误");
    }
}
