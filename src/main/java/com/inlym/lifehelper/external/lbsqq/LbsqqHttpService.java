package com.inlym.lifehelper.external.lbsqq;

import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.external.lbsqq.model.ConvertLocation2AddressResponse;
import com.inlym.lifehelper.external.lbsqq.model.LbsqqLocateIPResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class LbsqqHttpService {
    private final Log logger = LogFactory.getLog(getClass());

    private final RestTemplate restTemplate = new RestTemplate();

    private final LbsqqProperties lbsqqProperties;

    /** 调用次数 */
    private Integer invokeCounter = 0;

    public LbsqqHttpService(LbsqqProperties lbsqqProperties) {
        this.lbsqqProperties = lbsqqProperties;
    }

    /**
     * 正常企业级项目中，只需要一个密钥就可以了，而不是这里的一组密钥列表。这里使用密钥列表的原因是：
     * 个人开发者的密钥存在并发限制和较低的免费额度，容易达到上限，因此申请多个密钥用于轮询调用。
     */
    private String getKey() {
        String[] keys = lbsqqProperties.getKeys();
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
     */
    @Cacheable("lbsqq:locate-ip")
    public LbsqqLocateIPResponse locateIP(String ip) throws ExternalHttpRequestException {

        String baseURL = "https://apis.map.qq.com/ws/location/v1/ip";
        String url = UriComponentsBuilder
            .fromHttpUrl(baseURL)
            .queryParam("ip", ip)
            .queryParam("key", getKey())
            .build()
            .toUriString();

        LbsqqLocateIPResponse data = restTemplate.getForObject(url, LbsqqLocateIPResponse.class);

        assert data != null;
        if (data.getStatus() == 0) {
            logger.info("[IP 定位] ip=" + ip + ", 请求结果 data=" + data);
            return data;
        }

        throw new ExternalHttpRequestException("IP 定位", url, data.getStatus(), data.getMessage());
    }

    /**
     * 逆地址解析 - 将经纬度坐标转换为位置描述
     * <p>
     * 文档地址：
     * https://lbs.qq.com/service/webService/webServiceGuide/webServiceGcoder
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public ConvertLocation2AddressResponse convertLocation2Address(double longitude, double latitude) throws Exception {
        String location = latitude + "," + longitude;

        String url = "https://apis.map.qq.com/ws/geocoder/v1";
        UriComponents uriBuilder = UriComponentsBuilder
            .fromHttpUrl(url)
            .queryParam("location", location)
            .queryParam("key", getKey())
            .build();

        ConvertLocation2AddressResponse data = restTemplate.getForObject(uriBuilder.toUriString(), ConvertLocation2AddressResponse.class);

        assert data != null;
        if (data.getStatus() != null && data.getStatus() == 0) {
            logger.debug("[逆地址解析] longitude=" + longitude + ", latitude=" + latitude + ", 请求结果 status=" + data.getStatus());
            return data;
        }

        throw new ExternalHttpRequestException("逆地址解析", url, data.getStatus(), data.getMessage());
    }
}
