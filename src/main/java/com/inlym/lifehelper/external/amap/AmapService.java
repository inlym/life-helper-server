package com.inlym.lifehelper.external.amap;

import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.external.amap.model.AmapLocateIPResponse;
import com.inlym.lifehelper.location.LocationService;
import com.inlym.lifehelper.location.model.IPLocation;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 高德位置服务封装类
 *
 * @author inlym
 * @since 2022-01-19 20:24
 **/
@Service
@Primary
public class AmapService implements LocationService {
    private final AmapHttpService amapHttpService;

    public AmapService(AmapHttpService amapHttpService) {this.amapHttpService = amapHttpService;}

    /**
     * IP 定位
     *
     * @param ip IP 地址
     */
    @Override
    public IPLocation locateIp(String ip) throws ExternalHttpRequestException {
        Assert.notNull(ip, "IP 地址不允许为空");
        AmapLocateIPResponse result = amapHttpService.locateIP(ip);

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
