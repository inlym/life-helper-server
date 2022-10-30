package com.inlym.lifehelper.weather.data;

import com.inlym.lifehelper.common.annotation.ClientIp;
import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.location.LocationService;
import com.inlym.lifehelper.location.pojo.IpLocation;
import com.inlym.lifehelper.weather.data.pojo.WeatherDataVO;
import com.inlym.lifehelper.weather.place.WeatherPlaceRepository;
import com.inlym.lifehelper.weather.place.entity.WeatherPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private final WeatherDataIntegrationService weatherDataIntegrationService;

    private final WeatherPlaceRepository weatherPlaceRepository;

    private final LocationService locationService;

    /**
     * 获取天气汇总信息（匿名方式）
     *
     * @since 1.0.0
     */
    @GetMapping("/weather")
    public WeatherDataVO getWeatherData(@ClientIp String ip) {
        IpLocation info;

        // 备注（2022.10.30）
        // 此处用了 `try...catch...` 语句的原因是：不想被全局异常捕获器接管，此处即便报错也要做降级处理。
        try {
            info = locationService.locateIpUpToCity(ip);
        } catch (Exception e) {
            // IP 定位可能出错，此处保证出错时能够返回正常的数据
            info = IpLocation.getDefault();
        }

        WeatherDataVO weatherData = weatherDataIntegrationService.getWeatherData(info.getLongitude(), info.getLatitude());
        weatherData.setLocationName(info.getCity());

        return weatherData;
    }

    /**
     * 获取汇总后的天气数据
     *
     * @param userId  用户 ID
     * @param placeId 天气地点 ID
     *
     * @since 1.5.0
     */
    @GetMapping(path = "/weather", params = "place_id")
    @UserPermission
    public WeatherDataVO getWeatherData(@UserId int userId, @RequestParam("place_id") String placeId) {
        WeatherPlace place = weatherPlaceRepository.findOneOrElseThrow(userId, placeId);
        return weatherDataIntegrationService.getWeatherData(place);
    }
}
