package com.inlym.lifehelper.location.position;

import com.inlym.lifehelper.extern.tencentmap.TencentMapService;
import com.inlym.lifehelper.extern.tencentmap.pojo.TencentMapLocateIpResponse;
import com.inlym.lifehelper.extern.tencentmap.pojo.TencentMapReverseGeocodingResponse;
import com.inlym.lifehelper.location.position.pojo.AddressComponent;
import com.inlym.lifehelper.location.position.pojo.GeographicCoordinate;
import com.inlym.lifehelper.location.position.pojo.IpLocation;
import lombok.NonNull;
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
public class LocationService {
    private static final String CHINA_NATION_NAME = "中国";

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
     * 获取省级行政区的简称
     *
     * @param province 省级行政区全称
     *
     * @since 1.2.2
     */
    private static String getProvinceShortName(String province) {
        String provinceSuffix = "省";
        String citySuffix = "市";
        String sarSuffix = "特别行政区";

        String nmg = "内蒙古自治区";
        String gx = "广西壮族自治区";
        String xz = "西藏自治区";
        String nx = "宁夏回族自治区";
        String xj = "新疆维吾尔自治区";

        if (province.contains(provinceSuffix)) {
            return province.replaceAll(provinceSuffix, "");
        }

        if (province.contains(citySuffix)) {
            return province.replaceAll(citySuffix, "");
        }

        if (province.contains(sarSuffix)) {
            return province.replaceAll(sarSuffix, "");
        }

        if (nmg.equals(province)) {
            return "内蒙古";
        }

        if (gx.equals(province)) {
            return "广西";
        }

        if (xz.equals(province)) {
            return "西藏";
        }

        if (nx.equals(province)) {
            return "宁夏";
        }

        if (xj.equals(province)) {
            return "新疆";
        }

        return "";
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
     * 获取 IP 地址粗略的地理位置描述（最多到市级）
     *
     * <h2>返回格式示例
     *
     * <li>浙江杭州
     * <li>上海
     * <li>美国
     *
     * @param ip IP 地址
     *
     * @since 1.1.0
     */
    public String getRoughIpRegion(String ip) {
        IpLocation location = locateIpUpToCity(ip);

        // 非国内地区，直接返回国家名称
        if (!CHINA_NATION_NAME.equals(location.getNation())) {
            return location.getNation();
        }

        // 省和市同名，表明是直辖市
        if (location
            .getProvince()
            .equals(location.getCity())) {
            return location
                .getProvince()
                .replaceAll("市", "");
        }

        // 其他情况，返回“省 + 市”
        String province = getProvinceShortName(location.getProvince());
        String city = location
            .getCity()
            .replaceAll("市", "");

        // 省和市去掉后缀后同名，则返回完整的省市名称，例如“吉林省吉林市”
        if (province.equals(city)) {
            return location.getProvince() + location.getCity();
        }

        return province + city;
    }
}
