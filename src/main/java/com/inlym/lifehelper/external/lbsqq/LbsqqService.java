package com.inlym.lifehelper.external.lbsqq;

import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.external.lbsqq.model.LbsqqLocateIPResponse;
import com.inlym.lifehelper.location.pojo.LocationInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 腾讯位置服务封装类
 *
 * @author inlym
 * @since 2022-01-18 22:01
 **/
@Service
public class LbsqqService {
    private final LbsqqHttpService lbsqqHttpService;

    public LbsqqService(LbsqqHttpService lbsqqHttpService) {this.lbsqqHttpService = lbsqqHttpService;}

    /**
     * IP 定位
     *
     * @param ip IP 地址
     */
    public LocationInfo locateIp(String ip) throws ExternalHttpRequestException {
        Assert.notNull(ip, "IP 地址不允许为空");
        Assert.isTrue(!ip.isEmpty(), "IP 地址不允许为空");

        LbsqqLocateIPResponse res = lbsqqHttpService.locateIp(ip);
        LbsqqLocateIPResponse.Result result = res.getResult();
        LbsqqLocateIPResponse.Result.Location location = result.getLocation();
        LbsqqLocateIPResponse.Result.AddressInfo addressInfo = result.getAddressInfo();

        LocationInfo locationInfo = new LocationInfo();
        locationInfo.setLongitude(location.getLongitude());
        locationInfo.setLatitude(location.getLatitude());
        locationInfo.setNation(addressInfo.getNation());
        locationInfo.setProvince(addressInfo.getProvince());
        locationInfo.setCity(addressInfo.getCity());
        locationInfo.setDistrict(addressInfo.getDistrict());

        return locationInfo;
    }
}
