package com.inlym.lifehelper.external.amap;

import com.inlym.lifehelper.external.amap.pojo.AmapLocateIpResponse;
import com.inlym.lifehelper.location.model.IPLocation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 高德位置服务封装类
 * <p>
 * [主要用途]
 * 对 HTTP 请求服务类做二次封装，形成内部可调用的方法。
 *
 * @author inlym
 * @since 2022-01-19 20:24
 **/
@Service
@Slf4j
public class AmapService {
    private final AmapHttpService amapHttpService;

    public AmapService(AmapHttpService amapHttpService) {this.amapHttpService = amapHttpService;}

    /**
     * IP 定位
     *
     * @param ip IP 地址
     */
    @SneakyThrows
    public IPLocation locateIp(String ip) {
        Assert.notNull(ip, "IP 地址不允许为空");
        AmapLocateIpResponse result = amapHttpService.locateIp(ip);

        String[] list = result
            .getLocation()
            .split(",");

        IPLocation ipLocation = new IPLocation();
        ipLocation.setLongitude(Double.valueOf(list[0]));
        ipLocation.setLatitude(Double.valueOf(list[1]));
        ipLocation.setCountry(result.getCountry());
        ipLocation.setProvince(result.getProvince());
        ipLocation.setCity(result.getCity());
        ipLocation.setDistrict(result.getDistrict());

        return ipLocation;
    }
}
