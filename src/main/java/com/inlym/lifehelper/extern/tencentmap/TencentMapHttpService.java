package com.inlym.lifehelper.extern.tencentmap;

import com.inlym.lifehelper.common.constant.RedisCacheCollector;
import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.extern.tencentmap.config.TencentMapProperties;
import com.inlym.lifehelper.extern.tencentmap.exception.InvalidIpException;
import com.inlym.lifehelper.extern.tencentmap.pojo.TencentMapListRegionResponse;
import com.inlym.lifehelper.extern.tencentmap.pojo.TencentMapLocateIpResponse;
import com.inlym.lifehelper.extern.tencentmap.pojo.TencentMapReverseGeocodingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 腾讯位置服务 - HTTP 请求调用服务
 *
 * <h2>主要用途
 * <p>仅用于封装处理 HTTP 请求，不做数据处理。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-14
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class TencentMapHttpService {
    /** 表示请求成功的状态码 */
    private static final int SUCCESS_STATUS = 0;

    private final RestTemplate restTemplate;

    private final TencentMapProperties properties;

    /** 开发者密钥调用次数 */
    private Integer invokeCounter = 0;

    /**
     * IP 定位
     *
     * @param ip IP 地址
     *
     * @see <a href="https://lbs.qq.com/service/webService/webServiceGuide/webServiceIp">IP 定位</a>
     * @since 1.0.0
     */
    @Cacheable(RedisCacheCollector.WE_MAP_LOCATE_IP)
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
    @Cacheable(RedisCacheCollector.WE_MAP_REVERSE_GEOCODING)
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

    /**
     * 获取省市区列表
     *
     * <h2>说明
     * <p>这个接口正常情况下，可能好几个月才调用一次，用于更新地址库，因此不需要做缓存。
     *
     * @see <a href="https://lbs.qq.com/service/webService/webServiceGuide/webServiceDistrict">获取省市区列表</a>
     */
    public TencentMapListRegionResponse listRegions() {
        String baseUrl = "https://apis.map.qq.com/ws/district/v1/list";
        String url = UriComponentsBuilder
            .fromHttpUrl(baseUrl)
            .queryParam("key", getKey())
            .build()
            .toUriString();

        TencentMapListRegionResponse data = restTemplate.getForObject(url, TencentMapListRegionResponse.class);

        assert data != null;
        if (data.getStatus() == SUCCESS_STATUS) {
            log.info("[获取省市区列表] 接口调用成功");
            return data;
        }
        throw new ExternalHttpRequestException("获取省市区列表", url, data.getStatus(), data.getMessage());
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
}
