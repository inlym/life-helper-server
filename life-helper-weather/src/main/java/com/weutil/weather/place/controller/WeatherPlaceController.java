package com.weutil.weather.place.controller;

import com.weutil.common.annotation.ClientIp;
import com.weutil.common.annotation.UserId;
import com.weutil.common.annotation.UserPermission;
import com.weutil.location.position.LocationService;
import com.weutil.location.position.pojo.IpLocation;
import com.weutil.weather.data.WeatherDataService;
import com.weutil.weather.data.pojo.BasicWeather;
import com.weutil.weather.data.pojo.WeatherNow;
import com.weutil.weather.place.entity.WeatherPlace;
import com.weutil.weather.place.pojo.WeChatChooseLocationDTO;
import com.weutil.weather.place.pojo.WeatherPlaceListVO;
import com.weutil.weather.place.pojo.WeatherPlaceVO;
import com.weutil.weather.place.service.WeatherPlaceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 天气地点控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/16
 * @since 2.1.0
 **/
@RestController
@RequiredArgsConstructor
@Slf4j
public class WeatherPlaceController {
    private final LocationService locationService;

    private final WeatherDataService weatherDataService;

    private final WeatherPlaceService weatherPlaceService;

    @GetMapping("/weather/places")
    @UserPermission
    public WeatherPlaceListVO getList(@UserId long userId, @ClientIp String ip) {
        WeatherPlaceListVO vo = new WeatherPlaceListVO();

        // 备注（2022.10.30）
        // 此处使用 `try...catch...` 来捕获错误而不是使用全局异常捕获器的原因是：IP 定位信息属于可有可无，不能因为该 API 无法使用导致整个接口挂。
        try {
            IpLocation ipLocation = locationService.locateIpUpToCity(ip);
            WeatherNow now = weatherDataService.getWeatherNow(ipLocation.getLongitude(), ipLocation.getLatitude());
            WeatherPlaceListVO.IpLocatedPlace ipLocated = WeatherPlaceListVO.IpLocatedPlace
                    .builder()
                    .name(ipLocation.getCity())
                    .weather(BasicWeather.from(now))
                    .build();
            vo.setIpLocated(ipLocated);
        } catch (Exception e) {
            log.error("该 IP 地址无法使用 IP 定位：{}", ip);
        }

        vo.setList(weatherPlaceService.getList(userId));

        return vo;
    }

    @PostMapping("/weather/place")
    @UserPermission
    public WeatherPlaceVO create(@UserId long userId, @Valid @RequestBody WeChatChooseLocationDTO dto) {
        WeatherPlace place = WeatherPlace
                .builder()
                .userId(userId)
                .name(dto.getName())
                .address(dto.getAddress())
                .longitude(dto.getLongitude())
                .latitude(dto.getLatitude())
                .build();

        return weatherPlaceService.convertToViewObject(weatherPlaceService.create(place));
    }

    @DeleteMapping("/weather/place/{id}")
    @UserPermission
    public WeatherPlaceVO delete(@UserId long userId, @NotBlank @PathVariable("id") long placeId) {
        weatherPlaceService.delete(userId, placeId);

        return WeatherPlaceVO
                .builder()
                .id(placeId)
                .build();
    }
}
