package com.inlym.lifehelper.location.position;

import com.inlym.lifehelper.extern.wemap.model.WeMapLocateIpResponse;
import com.inlym.lifehelper.extern.wemap.model.WeMapReverseGeocodeResponse;
import com.inlym.lifehelper.extern.wemap.service.WeMapApiService;
import com.inlym.lifehelper.location.position.pojo.AddressComponent;
import com.inlym.lifehelper.location.position.pojo.IpLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 位置信息服务
 *
 * @author inlym
 * @date 2022-01-19
 * @since 1.0.0
 **/
@Service
@RequiredArgsConstructor
public class LocationService {
    private static final String CHINA_NATION_NAME = "中国";

    private final WeMapApiService weMapApiService;

    /**
     * 逆地址解析
     *
     * @param longitude 纬度
     * @param latitude  经度
     *
     * @since 1.0.0
     */
    public AddressComponent reverseGeocode(double longitude, double latitude) {
        WeMapReverseGeocodeResponse data = weMapApiService.reverseGeocode(longitude, latitude);
        WeMapReverseGeocodeResponse.AddressComponent ac = data
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

    /**
     * IP 定位精确到市级（仅限于国内城市）
     *
     * @param ip IP 地址
     *
     * @since 1.2.2
     */
    public IpLocation locateIpUpToCity(String ip) {
        IpLocation location = locateIp(ip);
        if (CHINA_NATION_NAME.equals(location.getNation()) && !StringUtils.hasText(location.getCity())) {
            AddressComponent component = reverseGeocode(location.getLongitude(), location.getLatitude());
            location.setProvince(component.getProvince());
            location.setCity(component.getCity());
            location.setDistrict(component.getDistrict());
        }

        return location;
    }

    /**
     * 获取 IP 定位获取的位置名称
     *
     * @param ip IP 地址
     *
     * @since 2.2.0
     */
    public String getIpLocationName(String ip) {
        WeMapLocateIpResponse.AddressInfo addressInfo = weMapApiService
            .locateIp(ip)
            .getResult()
            .getAddressInfo();

        if (addressInfo.getCity() != null) {
            return addressInfo.getCity();
        }
        if (addressInfo.getProvince() != null) {
            return addressInfo.getProvince();
        }
        if (addressInfo.getNation() != null) {
            return addressInfo.getNation();
        }
        return "未知";
    }

    /**
     * IP 定位
     *
     * @param ip IP 地址
     *
     * @since 1.0.0
     */
    private IpLocation locateIp(String ip) {
        WeMapLocateIpResponse data = weMapApiService.locateIp(ip);

        IpLocation info = new IpLocation();
        BeanUtils.copyProperties(data
                                     .getResult()
                                     .getLocation(), info);
        BeanUtils.copyProperties(data
                                     .getResult()
                                     .getAddressInfo(), info);

        return info;
    }
}
