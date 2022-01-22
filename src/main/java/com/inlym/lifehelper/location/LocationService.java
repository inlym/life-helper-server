package com.inlym.lifehelper.location;

import com.inlym.lifehelper.external.amap.AmapService;
import com.inlym.lifehelper.location.model.IPLocation;
import com.inlym.lifehelper.location.model.LocationCoordinate;
import lombok.NonNull;
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
     * 解析经纬度字符串
     * <p>
     * [注意事项]
     * 一般用在控制器中解析参数，在这之前已通过数据校验。
     *
     * @param locationString 经纬度字符串
     */
    public static LocationCoordinate parseLocationString(@NonNull String locationString) {
        String[] list = locationString.split(",");
        double longitude = Double.parseDouble(list[0]);
        double latitude = Double.parseDouble(list[1]);

        return new LocationCoordinate(longitude, latitude);
    }

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
