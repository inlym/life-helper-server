package com.inlym.lifehelper.weather;

import com.inlym.lifehelper.common.constant.CustomRequestAttribute;
import com.inlym.lifehelper.common.validation.LocationString;
import com.inlym.lifehelper.external.hefeng.model.*;
import com.inlym.lifehelper.location.LocationService;
import com.inlym.lifehelper.location.model.LocationCoordinate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 天气数据控制器
 *
 * @author inlym
 * @since 2022-01-21 23:36
 **/
@RestController
@Validated
public class WeatherController {
    private final WeatherService weatherService;

    private final LocationService locationService;

    private final HttpServletRequest request;

    public WeatherController(WeatherService weatherService, LocationService locationService, HttpServletRequest request) {
        this.weatherService = weatherService;
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
     * 获取实时天气
     *
     * @param location `120.12,30.34` 格式的经纬度字符串
     */
    @GetMapping("/weather/now")
    public WeatherNow getWeatherNow(@LocationString @RequestParam(name = "location", required = false) String location) {
        LocationCoordinate coordinate = getLocationCoordinate(location);

        return weatherService.getWeatherNow(coordinate.getLongitude(), coordinate.getLatitude());
    }

    /**
     * 获取未来15天的逐天天气预报
     *
     * @param location `120.12,30.34` 格式的经纬度字符串
     */
    @GetMapping("/weather/15d")
    public Object get15DaysWeatherDailyForecast(@LocationString @RequestParam(name = "location", required = false) String location) {
        LocationCoordinate coordinate = getLocationCoordinate(location);
        WeatherDailyForecast[] list = weatherService.get15DaysWeatherDailyForecast(coordinate.getLongitude(), coordinate.getLatitude());

        Map<String, Object> map = new HashMap<>(16);
        map.put("list", list);

        return map;
    }

    /**
     * 获取未来24小时的逐小时天气预报
     *
     * @param location `120.12,30.34` 格式的经纬度字符串
     */
    @GetMapping("/weather/24h")
    public Object get24HoursWeatherHourlyForecast(@LocationString @RequestParam(name = "location", required = false) String location) {
        LocationCoordinate coordinate = getLocationCoordinate(location);
        WeatherHourlyForecast[] list = weatherService.get24HoursWeatherHourlyForecast(coordinate.getLongitude(), coordinate.getLatitude());

        Map<String, Object> map = new HashMap<>(16);
        map.put("list", list);

        return map;
    }

    /**
     * 获取分钟级降水
     *
     * @param location `120.12,30.34` 格式的经纬度字符串
     */
    @GetMapping("/weather/rain")
    public MinutelyRain getMinutelyRain(@LocationString @RequestParam(name = "location", required = false) String location) {
        LocationCoordinate coordinate = getLocationCoordinate(location);

        return weatherService.getMinutelyRain(coordinate.getLongitude(), coordinate.getLatitude());
    }

    /**
     * 获取天气生活指数
     *
     * @param location `120.12,30.34` 格式的经纬度字符串
     */
    @GetMapping("/weather/indices")
    public Object getIndices(@LocationString @RequestParam(name = "location", required = false) String location) {
        LocationCoordinate coordinate = getLocationCoordinate(location);
        WeatherIndices[] list = weatherService.getIndices(coordinate.getLongitude(), coordinate.getLatitude());

        Map<String, Object> map = new HashMap<>(16);
        map.put("list", list);

        return map;
    }
}
