package com.inlym.lifehelper.location;

import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.location.model.IPLocation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 位置服务接口
 *
 * @author inlym
 * @since 2022-01-18 22:25
 **/
@RestController
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {this.locationService = locationService;}

    @GetMapping("/location")
    public IPLocation getLocationByIP(@RequestParam("ip") String ip) throws ExternalHttpRequestException {
        return locationService.LocateIP(ip);
    }
}
