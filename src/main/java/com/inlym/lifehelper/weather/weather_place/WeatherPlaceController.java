package com.inlym.lifehelper.weather.weather_place;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.weather.weather_place.entity.WeatherPlace;
import com.inlym.lifehelper.weather.weather_place.pojo.WeixinChooseLocationDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 天气地点控制器
 *
 * @author inlym
 * @date 2022-02-13 20:50
 **/
@RestController
public class WeatherPlaceController {
    private final WeatherPlaceService weatherPlaceService;

    public WeatherPlaceController(WeatherPlaceService weatherPlaceService) {
        this.weatherPlaceService = weatherPlaceService;
    }

    @ApiOperation("新增天气地点")
    @PostMapping("/weather/place")
    @UserPermission
    public WeatherPlace add(@Validated @RequestBody WeixinChooseLocationDTO dto, @UserId int userId) {
        return weatherPlaceService.add(userId, dto);
    }
}
