package com.inlym.lifehelper.location.position;

import com.inlym.lifehelper.common.annotation.ClientIp;
import com.inlym.lifehelper.location.position.pojo.IpInfoVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 位置服务控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/4/18
 * @since 1.1.0
 **/
@RestController
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * 获取客户端 IP 对应的位置信息
     *
     * @param ip 客户端 IP 地址
     *
     * @since 1.1.0
     */
    @GetMapping("/ip")
    public IpInfoVO getIp(@ClientIp String ip) {
        try {
            String region = locationService.getRoughIpRegion(ip);

            return IpInfoVO
                .builder()
                .ip(ip)
                .region(region)
                .build();
        } catch (Exception e) {
            // IP 定位可能出错，此处保证出错时能够返回 IP 地址
            return IpInfoVO
                .builder()
                .ip(ip)
                .build();
        }
    }
}
