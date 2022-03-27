package com.inlym.lifehelper.weather.weatherdata;

import com.inlym.lifehelper.common.annotation.ClientIp;
import com.inlym.lifehelper.location.LocationService;
import com.inlym.lifehelper.location.pojo.LocationInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
public class WeatherDataController {
    private final WeatherMixedDataService weatherMixedDataService;

    private final LocationService locationService;

    public WeatherDataController(WeatherMixedDataService weatherMixedDataService, LocationService locationService) {
        this.weatherMixedDataService = weatherMixedDataService;
        this.locationService = locationService;
    }

    /**
     * 获取天气汇总信息（匿名方式）
     */
    @GetMapping("/weather")
    public Map<String, Object> getMixedWeatherData(@ClientIp String ip) {
        LocationInfo info = locationService.locateIp(ip);
        String locationName = info.getCity() + info.getDistrict();
        String locationDesc = info.getProvince();

        Map<String, String> locationData = Map.of("name", locationName, "desc", locationDesc);

        var mixedData = weatherMixedDataService.getMixedWeatherData(info.getLongitude(), info.getLatitude());
        mixedData.put("location", locationData);

        return mixedData;
    }
}
