package com.inlym.lifehelper.weather.place;

import com.inlym.lifehelper.location.LocationService;
import com.inlym.lifehelper.location.pojo.AddressComponent;
import com.inlym.lifehelper.weather.place.entity.WeatherPlace;
import com.inlym.lifehelper.weather.place.pojo.WeatherPlaceVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 天气地点服务
 *
 * <h2>主要用途
 * <p>封装对“天气地点”数据的增删改查。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/21
 * @since 1.5.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherPlaceService {
    private final WeatherPlaceRepository weatherPlaceRepository;

    private final LocationService locationService;

    /**
     * 将天气地点实体转化为可用于客户端展示使用的视图对象
     *
     * @param place 天气地点实体
     *
     * @since 1.5.0
     */
    public WeatherPlaceVO convert(WeatherPlace place) {
        return WeatherPlaceVO
            .builder()
            .id(place.getPlaceId())
            .userId(place.getUserId())
            .name(place.getName())
            .address(place.getAddress())
            .province(place.getProvince())
            .city(place.getCity())
            .district(place.getDistrict())
            .build();
    }

    /**
     * 创建一条新的天气地点记录
     *
     * @param place 天气地点
     *
     * @since 1.5.0
     */
    public WeatherPlace create(WeatherPlace place) {
        // 根据经纬度填充省市区信息
        if (place.getLongitude() != null && place.getLatitude() != null) {
            AddressComponent ac = locationService.reverseGeocoding(place.getLongitude(), place.getLatitude());
            place.setProvince(ac.getProvince());
            place.setCity(ac.getCity());
            place.setDistrict(ac.getDistrict());
        }

        return weatherPlaceRepository.create(place);
    }

    /**
     * 删除一个天气地点
     *
     * @param userId  用户 ID
     * @param placeId 天气地点 ID
     *
     * @since 1.5.0
     */
    public void delete(int userId, String placeId) {
        weatherPlaceRepository.delete(userId, placeId);
    }

    /**
     * 获取所有记录
     *
     * @param userId 用户 ID
     *
     * @since 1.5.0
     */
    public List<WeatherPlace> list(int userId) {
        return weatherPlaceRepository.list(userId);
    }
}
