package com.inlym.lifehelper.location;

import com.inlym.lifehelper.external.amap.AmapService;
import com.inlym.lifehelper.location.model.IPLocation;
import com.inlym.lifehelper.location.model.LocationCoordinate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 位置信息服务
 *
 * @author inlym
 * @since 2022-01-19 20:42
 **/
@Service
@Slf4j
public class LocationService {
    private final AmapService amapService;

    public LocationService(AmapService amapService) {this.amapService = amapService;}

    /**
     * IP 定位
     *
     * @param ip IP 地址
     */
    public IPLocation locateIp(String ip) {
        return amapService.locateIp(ip);
    }

    /**
     * 通过 IP 地址换取经纬度坐标
     *
     * @param ip IP 地址
     */
    public LocationCoordinate getLocationCoordinateByIp(String ip) {
        IPLocation ipLocation = locateIp(ip);
        return new LocationCoordinate(ipLocation.getLongitude(), ipLocation.getLatitude());
    }
}
