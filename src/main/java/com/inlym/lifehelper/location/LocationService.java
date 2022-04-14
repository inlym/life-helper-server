package com.inlym.lifehelper.location;

import com.inlym.lifehelper.external.tencentmap.TencentMapService;
import com.inlym.lifehelper.external.tencentmap.pojo.TencentMapLocateIpResponse;
import com.inlym.lifehelper.external.tencentmap.pojo.TencentMapReverseGeocodingResponse;
import com.inlym.lifehelper.location.pojo.AddressComponent;
import com.inlym.lifehelper.location.pojo.GeographicCoordinate;
import com.inlym.lifehelper.location.pojo.IpLocation;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 位置信息服务
 *
 * @author inlym
 * @date 2022-01-19
 * @since 1.0.0
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
     * <h2>注意事项
     * <li>一般用在控制器中解析参数，在这之前已通过数据校验。
     *
     * @param locationString 经纬度字符串
     *
     * @since 1.0.0
     */
    public static GeographicCoordinate parseLocationString(@NonNull String locationString) {
        String[] list = locationString.split(",");
        double longitude = Double.parseDouble(list[0]);
        double latitude = Double.parseDouble(list[1]);

        return new GeographicCoordinate(longitude, latitude);
    }

    /**
     * IP 定位
     *
     * @param ip IP 地址
     *
     * @since 1.0.0
     */
    private IpLocation locateIp(String ip) {
        TencentMapLocateIpResponse data = tencentMapService.locateIp(ip);

        IpLocation info = new IpLocation();
        BeanUtils.copyProperties(data
            .getResult()
            .getLocation(), info);
        BeanUtils.copyProperties(data
            .getResult()
            .getAddressInfo(), info);

        return info;
    }

    /**
     * 加强版的 IP 定位
     *
     * <h2>说明
     * <p>普通的 IP 定位，实测获取的“市”和“县”均可能为空，利用从普通的 IP 定位获取的经纬度，使用逆地址解析再反查一遍，能够获取不为空的省市区
     * 信息（文档说“区县”值可能为空，但实测目前未遇到该情况）。
     *
     * @param ip IP 地址
     *
     * @since 1.0.0
     */
    public IpLocation locateIpPlus(String ip) {
        IpLocation location = locateIp(ip);
        if (location
            .getDistrict()
            .length() == 0) {
            AddressComponent component = reverseGeocoding(location.getLongitude(), location.getLatitude());
            location.setProvince(component.getProvince());
            location.setCity(component.getCity());
            location.setDistrict(component.getDistrict());
        }

        return location;
    }

    /**
     * 逆地址解析
     *
     * @param longitude 纬度
     * @param latitude  经度
     *
     * @since 1.0.0
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
