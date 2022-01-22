package com.inlym.lifehelper.external.lbsqq;

import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.external.lbsqq.model.LbsqqLocateIPResponse;
import com.inlym.lifehelper.location.model.IPLocation;
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
    public IPLocation locateIp(String ip) throws ExternalHttpRequestException {
        Assert.notNull(ip, "IP 地址不允许为空");
        Assert.isTrue(!ip.isEmpty(), "IP 地址不允许为空");

        LbsqqLocateIPResponse res = lbsqqHttpService.locateIp(ip);
        LbsqqLocateIPResponse.Result result = res.getResult();
        LbsqqLocateIPResponse.Result.Location location = result.getLocation();
        LbsqqLocateIPResponse.Result.AddressInfo addressInfo = result.getAddressInfo();

        IPLocation ipLocation = new IPLocation();
        ipLocation.setLongitude(location.getLongitude());
        ipLocation.setLatitude(location.getLatitude());
        ipLocation.setCountry(addressInfo.getNation());
        ipLocation.setProvince(addressInfo.getProvince());
        ipLocation.setCity(addressInfo.getCity());
        ipLocation.setDistrict(addressInfo.getDistrict());

        return ipLocation;
    }
}
