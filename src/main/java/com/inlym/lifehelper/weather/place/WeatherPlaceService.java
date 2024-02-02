package com.inlym.lifehelper.weather.place;

import com.inlym.lifehelper.location.position.LocationService;
import com.inlym.lifehelper.location.position.pojo.AddressComponent;
import com.inlym.lifehelper.weather.data.WeatherDataService;
import com.inlym.lifehelper.weather.data.pojo.BasicWeather;
import com.inlym.lifehelper.weather.data.pojo.WeatherNow;
import com.inlym.lifehelper.weather.place.entity.WeatherPlace;
import com.inlym.lifehelper.weather.place.exception.WeatherPlaceOverflowException;
import com.inlym.lifehelper.weather.place.pojo.WeatherPlaceVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    /** 允许添加的最大天气地点数量 */
    public static final int MAX_WEATHER_PLACE_NUMBER = 10;

    private final WeatherPlaceRepository weatherPlaceRepository;

    private final LocationService locationService;

    private final WeatherDataService weatherDataService;

    /**
     * 将天气地点实体转化为可用于客户端展示使用的视图对象
     *
     * @param place 天气地点实体
     *
     * @since 1.5.0
     */
    public WeatherPlaceVO convertToViewObject(WeatherPlace place) {
        return WeatherPlaceVO
            .builder()
            .id(place.getPlaceId())
            .name(place.getName())
            .region(place.getCity() + place.getDistrict())
            .build();
    }

    /**
     * 转化为天气地点视图对象
     *
     * @param place 天气地点实体
     * @param now   实时天气数据
     *
     * @since 1.5.0
     */
    public WeatherPlaceVO convertToViewObject(WeatherPlace place, WeatherNow now) {
        WeatherPlaceVO vo = convertToViewObject(place);
        vo.setWeather(BasicWeather.from(now));

        return vo;
    }

    /**
     * 创建一条新的天气地点记录
     *
     * @param place 天气地点
     *
     * @since 1.5.0
     */
    public WeatherPlace create(WeatherPlace place) {
        // 检测是否已超出数量限制
        if (weatherPlaceRepository.count(place.getUserId()) >= MAX_WEATHER_PLACE_NUMBER) {
            throw WeatherPlaceOverflowException.create(place.getUserId());
        }

        // 根据经纬度填充省市区信息
        if (place.getLongitude() != null && place.getLatitude() != null) {
            AddressComponent ac = locationService.reverseGeocoding(place.getLongitude(), place.getLatitude());
            place.setProvince(ac.getProvince());
            place.setCity(ac.getCity());
            place.setDistrict(ac.getDistrict());
            // 获取和风天气的 LocationId
            place.setLocationId(weatherDataService.getUniqueLocationId(place.getLongitude(), place.getLatitude()));
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
    public void delete(long userId, String placeId) {
        weatherPlaceRepository.delete(userId, placeId);
    }

    /**
     * 获取所有记录
     *
     * @param userId 用户 ID
     *
     * @since 1.5.0
     */
    public List<WeatherPlaceVO> getList(long userId) {
        List<WeatherPlaceVO> list = new ArrayList<>();
        List<WeatherPlace> places = weatherPlaceRepository.list(userId);

        if (places.size() > 0) {
            List<WeatherNow> weatherNowList = places
                .stream()
                .map(place -> weatherDataService.getWeatherNowAsync(place.getLocationId()))
                .map(CompletableFuture::join)
                .toList();

            for (int i = 0; i < places.size(); i++) {
                list.add(convertToViewObject(places.get(i), weatherNowList.get(i)));
            }
        }

        return list;
    }
}
