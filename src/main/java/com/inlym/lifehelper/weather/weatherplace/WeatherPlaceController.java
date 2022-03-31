package com.inlym.lifehelper.weather.weatherplace;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.weather.weatherplace.entity.WeatherPlace;
import com.inlym.lifehelper.weather.weatherplace.pojo.WeixinChooseLocationDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 天气地点控制器
 *
 * @author inlym
 * @date 2022-02-13
 **/
@RestController
public class WeatherPlaceController {
    private final WeatherPlaceService weatherPlaceService;

    public WeatherPlaceController(WeatherPlaceService weatherPlaceService) {
        this.weatherPlaceService = weatherPlaceService;
    }

    /**
     * 获取天气地点列表
     *
     * @param userId 用户 ID
     *
     * @since 1.0.0
     */
    @GetMapping("/weather/places")
    @UserPermission
    public Map<String, Object> list(@UserId int userId) {
        return Map.of("list", weatherPlaceService.list(userId));
    }

    /**
     * 新增天气地点
     *
     * @param dto    客户端提交的位置数据
     * @param userId 用户 ID
     *
     * @since 1.0.0
     */
    @PostMapping("/weather/place")
    @UserPermission
    public WeatherPlace add(@Validated @RequestBody WeixinChooseLocationDTO dto, @UserId int userId) {
        return weatherPlaceService.add(userId, dto);
    }

    /**
     * 删除一条天气地点
     *
     * @param id     天气城市 ID
     * @param userId 用户 ID
     *
     * @since 1.0.0
     */
    @DeleteMapping("/weather/place/{id}")
    @UserPermission
    public Map<String, Object> delete(@PathVariable("id") int id, @UserId int userId) {
        weatherPlaceService.delete(userId, id);
        return Map.of("id", id);
    }
}
