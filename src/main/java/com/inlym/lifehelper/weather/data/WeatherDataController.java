package com.inlym.lifehelper.weather.data;

import com.inlym.lifehelper.common.annotation.ClientIp;
import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.location.LocationService;
import com.inlym.lifehelper.location.pojo.IpLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 天气数据控制器
 *
 * @author inlym
 * @date 2022-02-19
 **/
@RestController
@Validated
@RequiredArgsConstructor
public class WeatherDataController {
    private final WeatherMixedDataService weatherMixedDataService;

    private final LocationService locationService;

    /**
     * 获取天气汇总信息（匿名方式）
     *
     * @since 1.0.0
     */
    @GetMapping("/weather")
    public Map<String, Object> getMixedWeatherData(@ClientIp String ip) {
        IpLocation info;

        try {
            info = locationService.locateIpUpToCity(ip);
        } catch (Exception e) {
            // IP 定位可能出错，此处保证出错时能够返回正常的数据
            // 目前设定出错时返回北京的数据
            info = new IpLocation();
            info.setLongitude(116.40);
            info.setLatitude(39.90);
            info.setDistrict("北京市");
            info.setCity("北京市");
            info.setProvince("");
        }

        // 备注（2022.05.09）：
        // 下一版本去掉 `location` 字段
        String name = info.getDistrict();
        String region = info.getProvince() + info.getCity();
        Map<String, String> location = Map.of("name", name, "region", region);

        Map<String, Object> mixedData = weatherMixedDataService.getMixedWeatherData(info.getLongitude(), info.getLatitude());
        mixedData.put("location", location);
        mixedData.put("ipLocationName", info.getCity());

        return mixedData;
    }

    /**
     * 获取天气汇总信息（根据天气地点 ID）
     */
    @GetMapping(path = "/weather", params = "place_id")
    @UserPermission
    public Map<String, Object> getMixedWeatherData(@RequestParam("place_id") int placeId, @UserId int userId) {
        return null;
    }
}
