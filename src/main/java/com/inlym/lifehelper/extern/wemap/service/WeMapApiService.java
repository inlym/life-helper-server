package com.inlym.lifehelper.extern.wemap.service;

import com.inlym.lifehelper.extern.wemap.config.WeMapProperties;
import com.inlym.lifehelper.extern.wemap.model.WeMapIpLocation;
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
public class WeMapApiService {
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
    public WeMapIpLocation locateIp(String ip) {
        ResponseEntity<WeMapIpLocation> entity = this.restClient
            .get()
            .uri("/ws/location/v1/ip?key={key}&ip={ip}", Map.of("ip", ip))
            .retrieve()
            .toEntity(WeMapIpLocation.class);

        return entity.getBody();
    }
}
