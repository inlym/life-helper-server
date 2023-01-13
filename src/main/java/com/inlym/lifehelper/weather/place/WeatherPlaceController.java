package com.inlym.lifehelper.weather.place;

import com.inlym.lifehelper.common.annotation.ClientIp;
import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.location.position.LocationService;
import com.inlym.lifehelper.location.position.pojo.IpLocation;
import com.inlym.lifehelper.weather.data.WeatherDataService;
import com.inlym.lifehelper.weather.data.pojo.BasicWeather;
import com.inlym.lifehelper.weather.data.pojo.WeatherNow;
import com.inlym.lifehelper.weather.place.entity.WeatherPlace;
import com.inlym.lifehelper.weather.place.pojo.WeChatChooseLocationDTO;
import com.inlym.lifehelper.weather.place.pojo.WeatherPlaceListVO;
import com.inlym.lifehelper.weather.place.pojo.WeatherPlaceVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 天气地点管理控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/21
 * @since 1.5.0
 **/
@RestController
@RequiredArgsConstructor
@Slf4j
public class WeatherPlaceController {
    private final WeatherPlaceService weatherPlaceService;

    private final LocationService locationService;

    private final WeatherDataService weatherDataService;

    /**
     * 新增天气地点
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @since 1.5.0
     */
    @PostMapping("/weather/place")
    @UserPermission
    public WeatherPlaceVO create(@UserId int userId, @Valid @RequestBody WeChatChooseLocationDTO dto) {
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

    /**
     * 删除一个天气地点
     *
     * @param userId  用户 ID
     * @param placeId 天气地点 ID
     *
     * @since 1.5.0
     */
    @DeleteMapping("/weather/place/{id}")
    @UserPermission
    public WeatherPlaceVO delete(@UserId int userId, @NotBlank @PathVariable("id") String placeId) {
        weatherPlaceService.delete(userId, placeId);

        return WeatherPlaceVO
            .builder()
            .id(placeId)
            .build();
    }

    /**
     * 获取天气地点列表
     *
     * @param userId 用户 ID
     *
     * @since 1.5.0
     */
    @GetMapping("/weather/places")
    @UserPermission
    public WeatherPlaceListVO getList(@UserId int userId, @ClientIp String ip) {
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
}
