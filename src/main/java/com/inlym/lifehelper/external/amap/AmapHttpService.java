package com.inlym.lifehelper.external.amap;

import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.external.amap.model.AmapLocateIPResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 高德开放平台 HTTP 请求封装类
 * <p>
 * [主要用途]
 * 将 HTTP API 接口封装为内部可直接调用的方法。
 * <p>
 * [注意事项]
 * 1. 仅对请求过程做封装，不要对响应数据做任何处理。
 *
 * @author inlym
 * @since 2022-01-19 19:07
 **/
@Service
public class AmapHttpService {
    private final Log logger = LogFactory.getLog(getClass());

    private final AmapProperties amapProperties;

    private final RestTemplate restTemplate = new RestTemplate();

    public AmapHttpService(AmapProperties amapProperties) {this.amapProperties = amapProperties;}

    /**
     * IP 定位
     *
     * @param ip IP 地址
     *
     * @see <a href="https://lbs.amap.com/api/webservice/guide/api/ipconfig">IP 定位</a>
     */
    @Cacheable("amap:locate-ip")
    public AmapLocateIPResponse locateIP(String ip) throws ExternalHttpRequestException {
        // 不含参数的请求地址前缀
        String baseURL = "https://restapi.amap.com/v5/ip";

        // 包含请求参数的完整请求地址
        String url = UriComponentsBuilder
            .fromHttpUrl(baseURL)
            .queryParam("key", amapProperties.getKey())
            .queryParam("type", "4")
            .queryParam("ip", ip)
            .build()
            .toUriString();

        AmapLocateIPResponse data = restTemplate.getForObject(url, AmapLocateIPResponse.class);

        assert data != null;
        if (data
            .getStatus()
            .equals("1")) {
            logger.info("[IP 定位] ip=" + ip + ", data=" + data);

            return data;
        }

        throw new ExternalHttpRequestException("IP 定位", url, data.getInfocode(), data.getInfo());
    }
}
