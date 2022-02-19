package com.inlym.lifehelper.weather.weatherdata;

import com.inlym.lifehelper.common.constant.CustomRequestAttribute;
import com.inlym.lifehelper.common.validation.LocationString;
import com.inlym.lifehelper.location.LocationService;
import com.inlym.lifehelper.location.pojo.LocationCoordinate;
import com.inlym.lifehelper.weather.weatherdata.pojo.AirNow;
import com.inlym.lifehelper.weather.weatherdata.pojo.MinutelyRain;
import com.inlym.lifehelper.weather.weatherdata.pojo.WeatherNow;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    private final WeatherDataService weatherDataService;

    private final WeatherMixedDataService weatherMixedDataService;

    private final LocationService locationService;

    private final HttpServletRequest request;

    public WeatherDataController(WeatherDataService weatherDataService, WeatherMixedDataService weatherMixedDataService, LocationService locationService, HttpServletRequest request) {
        this.weatherDataService = weatherDataService;
        this.weatherMixedDataService = weatherMixedDataService;
        this.locationService = locationService;
        this.request = request;
    }

    /**
     * 获取经纬度坐标
     * <p>
     * [主要逻辑]
     * 如果请求传了有效的经纬度字符串则直接使用，否则通过 IP 地址换取经纬度坐标。
     *
     * @param location `120.12,30.34` 格式的经纬度字符串
     */
    private LocationCoordinate getLocationCoordinate(String location) {
        if (location != null) {
            return LocationService.parseLocationString(location);
        } else {
            String ip = (String) request.getAttribute(CustomRequestAttribute.CLIENT_IP);
            return locationService.getLocationCoordinateByIp(ip);
        }
    }

    /**
     * 获取天气汇总信息
     *
     * @param location `120.12,30.34` 格式的经纬度字符串
     */
    @GetMapping("/weather")
    public Map<String, Object> getMixedWeatherData(@LocationString @RequestParam(name = "location", required = false) String location) {
        LocationCoordinate coordinate = getLocationCoordinate(location);
        return weatherMixedDataService.getMixedWeatherData(coordinate.getLongitude(), coordinate.getLatitude());
    }

    /**
     * 获取实时天气
     *
     * @param location `120.12,30.34` 格式的经纬度字符串
     */
    @GetMapping("/weather/now")
    public WeatherNow getWeatherNow(@LocationString @RequestParam(name = "location", required = false) String location) {
        LocationCoordinate coordinate = getLocationCoordinate(location);
        return weatherDataService.getWeatherNow(coordinate.getLongitude(), coordinate.getLatitude());
    }

    /**
     * 获取未来15天的逐天天气预报
     *
     * @param location `120.12,30.34` 格式的经纬度字符串
     */
    @GetMapping("/weather/15d")
    public Map<String, Object> getWeather15D(@LocationString @RequestParam(name = "location", required = false) String location) {
        LocationCoordinate coordinate = getLocationCoordinate(location);
        return Map.of("list", weatherDataService.getWeather15D(coordinate.getLongitude(), coordinate.getLatitude()));
    }

    /**
     * 获取未来24小时的逐小时天气预报
     *
     * @param location `120.12,30.34` 格式的经纬度字符串
     */
    @GetMapping("/weather/24h")
    public Map<String, Object> getWeather24H(@LocationString @RequestParam(name = "location", required = false) String location) {
        LocationCoordinate coordinate = getLocationCoordinate(location);
        return Map.of("list", weatherDataService.getWeather24H(coordinate.getLongitude(), coordinate.getLatitude()));
    }

    /**
     * 获取分钟级降水
     *
     * @param location `120.12,30.34` 格式的经纬度字符串
     */
    @GetMapping("/weather/rain")
    public MinutelyRain getMinutely(@LocationString @RequestParam(name = "location", required = false) String location) {
        LocationCoordinate coordinate = getLocationCoordinate(location);
        return weatherDataService.getMinutely(coordinate.getLongitude(), coordinate.getLatitude());
    }

    /**
     * 获取未来3天天气生活指数
     *
     * @param location `120.12,30.34` 格式的经纬度字符串
     */
    @GetMapping("/weather/indices/3d")
    public Map<String, Object> getIndices3D(@LocationString @RequestParam(name = "location", required = false) String location) {
        LocationCoordinate coordinate = getLocationCoordinate(location);
        return Map.of("list", weatherDataService.getIndices3D(coordinate.getLongitude(), coordinate.getLatitude()));
    }

    /**
     * 获取实时空气质量
     *
     * @param location `120.12,30.34` 格式的经纬度字符串
     */
    @GetMapping("/weather/air/now")
    public AirNow getAirNow(@LocationString @RequestParam(name = "location", required = false) String location) {
        LocationCoordinate coordinate = getLocationCoordinate(location);
        return weatherDataService.getAirNow(coordinate.getLongitude(), coordinate.getLatitude());
    }

    /**
     * 获取未来5天空气质量预报
     *
     * @param location `120.12,30.34` 格式的经纬度字符串
     */
    @GetMapping("/weather/air/5d")
    public Map<String, Object> getAir5D(@LocationString @RequestParam(name = "location", required = false) String location) {
        LocationCoordinate coordinate = getLocationCoordinate(location);
        return Map.of("list", weatherDataService.getAir5D(coordinate.getLongitude(), coordinate.getLatitude()));
    }
}
