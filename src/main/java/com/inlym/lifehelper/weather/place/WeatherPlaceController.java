package com.inlym.lifehelper.weather.place;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.weather.place.entity.WeatherPlace;
import com.inlym.lifehelper.weather.place.pojo.WeChatChooseLocationDTO;
import com.inlym.lifehelper.weather.place.pojo.WeatherPlaceListVO;
import com.inlym.lifehelper.weather.place.pojo.WeatherPlaceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 天气地点管理控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/21
 * @since 1.5.0
 **/
@RestController
@RequiredArgsConstructor
public class WeatherPlaceController {
    private final WeatherPlaceService weatherPlaceService;

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

        return weatherPlaceService.convert(weatherPlaceService.create(place));
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
    public WeatherPlaceListVO getList(@UserId int userId) {
        return WeatherPlaceListVO
            .builder()
            .list(weatherPlaceService.getList(userId))
            .build();
    }
}
