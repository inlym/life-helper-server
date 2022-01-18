package com.inlym.lifehelper.location;

import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.external.lbsqq.LbsqqService;
import com.inlym.lifehelper.external.lbsqq.model.LbsqqLocateIPResponse;
import com.inlym.lifehelper.location.model.IPLocation;
import org.springframework.stereotype.Service;

/**
 * 位置服务
 *
 * @author inlym
 * @since 2022-01-18 21:46
 **/
@Service
public class LocationService {
    private final LbsqqService lbsqqService;

    public LocationService(LbsqqService lbsqqService) {this.lbsqqService = lbsqqService;}

    public IPLocation LocateIP(String ip) throws ExternalHttpRequestException {
        LbsqqLocateIPResponse result = lbsqqService.locateIP(ip);

        IPLocation ipLocation = new IPLocation();
        ipLocation.setLongitude(result
            .getResult()
            .getLocation()
            .getLongitude());

        ipLocation.setLatitude(result
            .getResult()
            .getLocation()
            .getLatitude());

        ipLocation.setNation(result
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

        ipLocation.setAdcode(String.valueOf(result
            .getResult()
            .getAddressInfo()
            .getAdcode()));

        return ipLocation;
    }
}
