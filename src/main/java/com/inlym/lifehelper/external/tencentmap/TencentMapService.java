package com.inlym.lifehelper.external.tencentmap;

import com.inlym.lifehelper.external.tencentmap.pojo.TencentMapLocateIpResponse;
import com.inlym.lifehelper.external.tencentmap.pojo.TencentMapReverseGeocodingResponse;
import lombok.NonNull;
import org.springframework.stereotype.Service;

/**
 * 腾讯位置服务相关方法
 *
 * @author inlym
 * @date 2022-02-14 20:06
 **/
@Service
public class TencentMapService {
    private final TencentMapHttpService tencentMapHttpService;

    public TencentMapService(TencentMapHttpService tencentMapHttpService) {
        this.tencentMapHttpService = tencentMapHttpService;
    }

    /**
     * 联结经纬度坐标，使其变成 `lat,lng` 的格式
     * <p>
     * [为什么要联结经纬度]
     * 最终发起 HTTP 请求时，经纬度坐标需要以 `location=lat,lng` 的格式传递。
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    private static String concatLocation(double longitude, double latitude) {
        // 将数值向下取整到 5 位小数
        double lng = Math.floor(longitude * 100000) / 100000;
        double lat = Math.floor(latitude * 100000) / 100000;

        return lat + "," + lng;
    }

    /**
     * IP 定位
     *
     * @param ip IP 地址
     */
    public TencentMapLocateIpResponse locateIp(@NonNull String ip) {
        return tencentMapHttpService.locateIp(ip);
    }

    /**
     * 逆地址解析（坐标位置描述）
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public TencentMapReverseGeocodingResponse reverseGeocoding(double longitude, double latitude) {
        String location = concatLocation(longitude, latitude);
        return tencentMapHttpService.reverseGeocoding(location);
    }
}
