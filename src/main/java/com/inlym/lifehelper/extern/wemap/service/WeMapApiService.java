package com.inlym.lifehelper.extern.wemap.service;

import com.inlym.lifehelper.common.constant.RedisCacheCollector;
import com.inlym.lifehelper.extern.wemap.config.WeMapProperties;
import com.inlym.lifehelper.extern.wemap.exception.WeMapApiException;
import com.inlym.lifehelper.extern.wemap.model.WeMapListRegionResponse;
import com.inlym.lifehelper.extern.wemap.model.WeMapLocateIpResponse;
import com.inlym.lifehelper.extern.wemap.model.WeMapReverseGeocodeResponse;
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
    public WeMapLocateIpResponse locateIp(String ip) {
        ResponseEntity<WeMapLocateIpResponse> response = this.restClient
            .get()
            .uri("/ws/location/v1/ip?key={key}&ip={ip}", Map.of("ip", ip))
            .retrieve()
            .toEntity(WeMapLocateIpResponse.class);

        WeMapLocateIpResponse body = response.getBody();
        if (body != null && body.getStatus() != SUCCESS_STATUS) {
            String message = "status=" + body.getStatus() + ", message=" + body.getMessage();
            log.error("[IP定位] 请求错误，错误描述 -> {}", message);
            throw new WeMapApiException(message);
        }

        return body;
    }

    /**
     * 获取省市区列表
     *
     * <h2>说明
     * <p>这个接口正常情况下，可能好几个月才调用一次，用于更新地址库，因此不需要做缓存。
     *
     * @see <a href="https://lbs.qq.com/service/webService/webServiceGuide/webServiceDistrict">获取省市区列表</a>
     */
    public WeMapListRegionResponse listRegion() {
        ResponseEntity<WeMapListRegionResponse> response = this.restClient
            .get()
            .uri("/ws/district/v1/list?key={key}")
            .retrieve()
            .toEntity(WeMapListRegionResponse.class);

        WeMapListRegionResponse body = response.getBody();
        if (body != null && body.getStatus() != SUCCESS_STATUS) {
            String message = "status=" + body.getStatus() + ", message=" + body.getMessage();
            log.error("[获取行政区划] 请求错误，错误描述 -> {}", message);
            throw new WeMapApiException(message);
        }

        return body;
    }

    /**
     * 逆地址解析（坐标位置描述）
     *
     * @param longitude 经度
     * @param latitude  纬度
     *
     * @see <a href="https://lbs.qq.com/service/webService/webServiceGuide/webServiceGcoder">逆地址解析（坐标位置描述）</a>
     */
    @Cacheable(RedisCacheCollector.WE_MAP_REVERSE_GEOCODE)
    public WeMapReverseGeocodeResponse reverseGeocode(double longitude, double latitude) {
        // 将数值向下取整到 5 位小数
        double lng = Math.floor(longitude * 100000) / 100000;
        double lat = Math.floor(latitude * 100000) / 100000;

        String location = lat + "," + lng;

        ResponseEntity<WeMapReverseGeocodeResponse> response = this.restClient
            .get()
            .uri("/ws/geocoder/v1?key={key}&location={location}", Map.of("location", location))
            .retrieve()
            .toEntity(WeMapReverseGeocodeResponse.class);

        WeMapReverseGeocodeResponse body = response.getBody();
        if (body != null && body.getStatus() != SUCCESS_STATUS) {
            String message = "status=" + body.getStatus() + ", message=" + body.getMessage();
            log.error("[逆地址解析] 请求错误，错误描述 -> {}", message);
            throw new WeMapApiException(message);
        }

        return body;
    }
}
