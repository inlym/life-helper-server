package com.inlym.lifehelper.external.lbsqq;

import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.external.lbsqq.model.LbsqqLocateIPResponse;
import com.inlym.lifehelper.location.LocationService;
import com.inlym.lifehelper.location.model.IPLocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 腾讯位置服务封装类
 *
 * @author inlym
 * @since 2022-01-18 22:01
 **/
@Service
public class LbsqqService implements LocationService {
    private final Log logger = LogFactory.getLog(getClass());

    private final LbsqqHttpService lbsqqHttpService;

    public LbsqqService(LbsqqHttpService lbsqqHttpService) {this.lbsqqHttpService = lbsqqHttpService;}

    /**
     * IP 定位
     *
     * @param ip IP 地址
     */
    @Override
    public IPLocation locateIP(String ip) throws ExternalHttpRequestException {
        Assert.notNull(ip, "IP 地址不允许为空");
        Assert.isTrue(!ip.isEmpty(), "IP 地址不允许为空");

        LbsqqLocateIPResponse result = lbsqqHttpService.locateIP(ip);

        IPLocation ipLocation = new IPLocation();
        ipLocation.setLongitude(result
            .getResult()
            .getLocation()
            .getLongitude());

        ipLocation.setLatitude(result
            .getResult()
            .getLocation()
            .getLatitude());

        ipLocation.setCountry(result
            .getResult()
            .getAddressInfo()
            .getNation());

        ipLocation.setProvince(result
            .getResult()
            .getAddressInfo()
            .getProvince());

        ipLocation.setCity(result
            .getResult()
            .getAddressInfo()
            .getCity());

        ipLocation.setDistrict(result
            .getResult()
            .getAddressInfo()
            .getDistrict());

        return ipLocation;
    }
}
