package com.inlym.lifehelper.weather.weatherdata;

import com.inlym.lifehelper.common.annotation.ClientIp;
import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.location.LocationService;
import com.inlym.lifehelper.location.pojo.IpLocation;
import com.inlym.lifehelper.weather.weatherplace.WeatherPlaceService;
import com.inlym.lifehelper.weather.weatherplace.entity.WeatherPlace;
import com.inlym.lifehelper.weather.weatherplace.pojo.WeatherPlaceBO;
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
public class WeatherDataController {
    private final WeatherMixedDataService weatherMixedDataService;

    private final LocationService locationService;

    private final WeatherPlaceService weatherPlaceService;

    public WeatherDataController(WeatherMixedDataService weatherMixedDataService, LocationService locationService, WeatherPlaceService weatherPlaceService) {
        this.weatherMixedDataService = weatherMixedDataService;
        this.locationService = locationService;
        this.weatherPlaceService = weatherPlaceService;
    }

    /**
     * 获取天气汇总信息（匿名方式）
     *
     * @since 1.0.0
     */
    @GetMapping("/weather")
    public Map<String, Object> getMixedWeatherData(@ClientIp String ip) {
        IpLocation info = locationService.locateIpPlus(ip);
        String locationName;
        String locationDesc;

        if (info
            .getDistrict()
            .length() == 0) {
            locationName = info.getCity();
            locationDesc = info.getProvince();
        } else {
            locationName = info.getDistrict();
            locationDesc = info.getProvince() + "，" + info.getCity();
        }

        Map<String, String> locationData = Map.of("name", locationName, "desc", locationDesc);

        Map<String, Object> mixedData = weatherMixedDataService.getMixedWeatherData(info.getLongitude(), info.getLatitude());
        mixedData.put("location", locationData);

        return mixedData;
    }

    /**
     * 获取天气汇总信息（根据天气地点 ID）
     */
    @GetMapping(path = "/weather", params = "place_id")
    @UserPermission
    public Map<String, Object> getMixedWeatherData(@RequestParam("place_id") int placeId, @UserId int userId) {
        WeatherPlace place = weatherPlaceService.findById(userId, placeId);
        assert place != null;

        WeatherPlaceBO bo = WeatherPlaceService.convertToWeatherPlaceBO(place);

        Map<String, Object> mixedData = weatherMixedDataService.getMixedWeatherData(place.getLongitude(), place.getLatitude());
        mixedData.put("place", bo);

        return mixedData;
    }
}
