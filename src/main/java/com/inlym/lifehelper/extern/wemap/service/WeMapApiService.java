package com.inlym.lifehelper.extern.wemap.service;

import com.inlym.lifehelper.common.constant.RedisCacheCollector;
import com.inlym.lifehelper.extern.wemap.config.WeMapProperties;
import com.inlym.lifehelper.extern.wemap.exception.WeMapApiException;
import com.inlym.lifehelper.extern.wemap.model.WeMapIpLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

/**
 * 腾讯位置服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/21
 * @since 2.1.0
 **/
@Service
@Slf4j
public class WeMapApiService {
    /** 表示请求成功的状态码 */
    private static final int SUCCESS_STATUS = 0;

    private final RestClient restClient;

    public WeMapApiService(WeMapProperties weMapProperties) {
        this.restClient = RestClient
            .builder()
            .baseUrl("https://apis.map.qq.com")
            .defaultUriVariables(Map.of("key", weMapProperties.getKey()))
            .build();
    }

    /**
     * IP 定位
     *
     * @param ip IP 地址
     *
     * @since 2.1.0
     */
    @Cacheable(RedisCacheCollector.WE_MAP_LOCATE_IP)
    public WeMapIpLocation locateIp(String ip) {
        ResponseEntity<WeMapIpLocation> response = this.restClient
            .get()
            .uri("/ws/location/v1/ip?key={key}&ip={ip}", Map.of("ip", ip))
            .retrieve()
            .toEntity(WeMapIpLocation.class);

        WeMapIpLocation body = response.getBody();
        if (body != null && body.getStatus() != SUCCESS_STATUS) {
            String message = "status=" + body.getStatus() + ", message=" + body.getMessage();
            log.error("[IP定位] 请求错误，错误描述 -> {}", message);
            throw new WeMapApiException(message);
        }

        return body;
    }
}
