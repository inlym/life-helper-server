package com.inlym.lifehelper.location.position;

import com.inlym.lifehelper.extern.tencentmap.TencentMapService;
import com.inlym.lifehelper.extern.tencentmap.pojo.TencentMapLocateIpResponse;
import com.inlym.lifehelper.extern.tencentmap.pojo.TencentMapReverseGeocodingResponse;
import com.inlym.lifehelper.location.position.pojo.AddressComponent;
import com.inlym.lifehelper.location.position.pojo.GeographicCoordinate;
import com.inlym.lifehelper.location.position.pojo.IpLocation;
import lombok.NonNull;
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

    private final TencentMapService tencentMapService;

    /**
     * 解析经纬度字符串
     * <p>
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
            AddressComponent component = reverseGeocoding(location.getLongitude(), location.getLatitude());
            location.setProvince(component.getProvince());
            location.setCity(component.getCity());
            location.setDistrict(component.getDistrict());
        }

        return location;
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
}
