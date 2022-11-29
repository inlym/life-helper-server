package com.inlym.lifehelper.extern.tencentmap;

import com.inlym.lifehelper.common.constant.RedisCacheCollector;
import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.extern.tencentmap.exception.InvalidIpException;
import com.inlym.lifehelper.extern.tencentmap.pojo.TencentMapLocateIpResponse;
import com.inlym.lifehelper.extern.tencentmap.pojo.TencentMapReverseGeocodingResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 腾讯位置服务 - HTTP 请求封装类
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-14
 **/
@Service
@Slf4j
public class TencentMapHttpService {
    /** 表示请求成功的状态码 */
    private static final int SUCCESS_STATUS = 0;

    private final RestTemplate restTemplate = new RestTemplate();

    private final TencentMapProperties properties;

    /** 开发者密钥调用次数 */
    private Integer invokeCounter = 0;

    public TencentMapHttpService(TencentMapProperties properties) {
        this.properties = properties;
    }

    /**
     * 获取开发者调用密钥
     *
     * <h2>说明
     * <p>正常企业级项目中，只需要一个密钥就可以了，而不是这里的一组密钥列表。这里使用密钥列表的原因是：
     * 个人开发者的密钥存在并发限制和较低的免费额度，容易达到上限，因此申请多个密钥用于轮询调用。
     */
    private String getKey() {
        String[] keys = properties.getKeys();
        String key = keys[invokeCounter % keys.length];
        invokeCounter++;

        return key;
    }

    /**
     * IP 定位
     *
     * @param ip IP 地址
     *
     * @see <a href="https://lbs.qq.com/service/webService/webServiceGuide/webServiceIp">IP 定位</a>
     * @since 1.0.0
     */
    @SneakyThrows
    @Cacheable(RedisCacheCollector.TENCENT_MAP_LOCATE_IP)
    public TencentMapLocateIpResponse locateIp(String ip) {
        String baseUrl = "https://apis.map.qq.com/ws/location/v1/ip";
        String url = UriComponentsBuilder
            .fromHttpUrl(baseUrl)
            .queryParam("ip", ip)
            .queryParam("key", getKey())
            .build()
            .toUriString();

        TencentMapLocateIpResponse data = restTemplate.getForObject(url, TencentMapLocateIpResponse.class);

        assert data != null;
        if (data.getStatus() == SUCCESS_STATUS) {
            log.info("[IP 定位] ip={}, data={}", ip, data);
            return data;
        } else {
            log.error("[IP 定位] url={}, status={}, message={}", url, data.getStatus(), data.getMessage());
            throw new InvalidIpException(data.getMessage());
        }
    }

    /**
     * 逆地址解析（坐标位置描述）
     *
     * @param location 经纬度字符串，格式：`lat,lng`
     *
     * @see <a href="https://lbs.qq.com/service/webService/webServiceGuide/webServiceGcoder">逆地址解析（坐标位置描述）</a>
     */
    @SneakyThrows
    @Cacheable(RedisCacheCollector.TENCENT_MAP_REVERSE_GEOCODING)
    public TencentMapReverseGeocodingResponse reverseGeocoding(String location) {
        String baseUrl = "https://apis.map.qq.com/ws/geocoder/v1/";
        String url = UriComponentsBuilder
            .fromHttpUrl(baseUrl)
            .queryParam("key", getKey())
            .queryParam("location", location)
            .build()
            .toUriString();

        TencentMapReverseGeocodingResponse data = restTemplate.getForObject(url, TencentMapReverseGeocodingResponse.class);

        assert data != null;
        if (data.getStatus() == SUCCESS_STATUS) {
            log.info("[逆地址解析] location={}, data={}", location, data);
            return data;
        }
        throw new ExternalHttpRequestException("逆地址解析", url, data.getStatus(), data.getMessage());
    }
}
