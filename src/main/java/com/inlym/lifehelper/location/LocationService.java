package com.inlym.lifehelper.location;

import com.inlym.lifehelper.external.tencentmap.TencentMapService;
import com.inlym.lifehelper.external.tencentmap.pojo.TencentMapLocateIpResponse;
import com.inlym.lifehelper.external.tencentmap.pojo.TencentMapReverseGeocodingResponse;
import com.inlym.lifehelper.location.pojo.AddressComponent;
import com.inlym.lifehelper.location.pojo.LocationCoordinate;
import com.inlym.lifehelper.location.pojo.LocationInfo;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 位置信息服务
 *
 * @author inlym
 * @date 2022-01-19
 **/
@Service
public class LocationService {
    private final TencentMapService tencentMapService;

    public LocationService(TencentMapService tencentMapService) {
        this.tencentMapService = tencentMapService;
    }

    /**
     * 解析经纬度字符串
     *
     * <li> （注意）一般用在控制器中解析参数，在这之前已通过数据校验。
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
    public LocationInfo locateIp(String ip) {
        TencentMapLocateIpResponse data = tencentMapService.locateIp(ip);

        LocationInfo info = new LocationInfo();
        BeanUtils.copyProperties(data
            .getResult()
            .getLocation(), info);
        BeanUtils.copyProperties(data
            .getResult()
            .getAddressInfo(), info);

        return info;
    }

    /**
     * 通过 IP 地址换取经纬度坐标
     *
     * @param ip IP 地址
     */
    public LocationCoordinate getLocationCoordinateByIp(String ip) {
        LocationInfo locationInfo = locateIp(ip);
        return new LocationCoordinate(locationInfo.getLongitude(), locationInfo.getLatitude());
    }

    /**
     * 逆地址解析
     *
     * @param longitude 纬度
     * @param latitude  经度
     */
    public AddressComponent reverseGeocoding(double longitude, double latitude) {
        TencentMapReverseGeocodingResponse data = tencentMapService.reverseGeocoding(longitude, latitude);
        TencentMapReverseGeocodingResponse.AddressComponent ac = data
            .getResult()
            .getAddressComponent();

        return AddressComponent
            .builder()
            .address(data
                .getResult()
                .getAddress())
            .recommendAddresses(data
                .getResult()
                .getFormattedAddresses()
                .getRecommend())
            .nation(ac.getNation())
            .province(ac.getProvince())
            .city(ac.getCity())
            .district(ac.getDistrict())
            .street(ac.getStreet())
            .build();
    }
}
