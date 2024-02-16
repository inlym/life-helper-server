package com.inlym.lifehelper.weather.place.service;

import com.inlym.lifehelper.common.exception.ResourceNotFoundException;
import com.inlym.lifehelper.location.position.LocationService;
import com.inlym.lifehelper.location.position.pojo.AddressComponent;
import com.inlym.lifehelper.weather.data.WeatherDataService;
import com.inlym.lifehelper.weather.data.pojo.BasicWeather;
import com.inlym.lifehelper.weather.data.pojo.WeatherNow;
import com.inlym.lifehelper.weather.place.entity.WeatherPlace;
import com.inlym.lifehelper.weather.place.exception.WeatherPlaceOverflowException;
import com.inlym.lifehelper.weather.place.mapper.WeatherPlaceMapper;
import com.inlym.lifehelper.weather.place.pojo.WeatherPlaceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.inlym.lifehelper.weather.place.entity.table.WeatherPlaceTableDef.WEATHER_PLACE;

/**
 * 天气地点服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/16
 * @since 2.1.0
 **/
@Service
@RequiredArgsConstructor
public class WeatherPlaceService {
    /** 允许添加的最大天气地点数量 */
    public static final int MAX_WEATHER_PLACE_NUMBER = 10;

    private final LocationService locationService;

    private final WeatherDataService weatherDataService;

    private final WeatherPlaceMapper weatherPlaceMapper;

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
            .id(place.getId())
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
     * @since 2.1.0
     */
    public WeatherPlace create(WeatherPlace place) {
        // 检测是否已超出数量限制
        long count = weatherPlaceMapper.selectCountByCondition(WEATHER_PLACE.USER_ID.eq(place.getUserId()));
        if (count >= MAX_WEATHER_PLACE_NUMBER) {
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

        weatherPlaceMapper.insertSelective(place);
        return weatherPlaceMapper.selectOneById(place.getId());
    }

    /**
     * 删除一条天气地点记录
     *
     * @param userId  所属用户 ID
     * @param placeId 天气地点 ID
     *
     * @since 2.1.0
     */
    public void delete(long userId, long placeId) {
        WeatherPlace place = weatherPlaceMapper.selectOneById(placeId);
        if (place != null && place.getUserId() == userId) {
            weatherPlaceMapper.deleteById(placeId);
        }
    }

    /**
     * 获取列表
     *
     * @param userId 用户 ID
     *
     * @since 2.1.0
     */
    public List<WeatherPlaceVO> getList(long userId) {
        List<WeatherPlaceVO> list = new ArrayList<>();
        List<WeatherPlace> places = weatherPlaceMapper.selectListByCondition(WEATHER_PLACE.USER_ID.eq(userId));

        if (!places.isEmpty()) {
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

    /**
     * 查找指定天气地点
     *
     * @param userId  用户 ID
     * @param placeId 天气地点 ID
     *
     * @since 2.1.0
     */
    public WeatherPlace findOne(long userId, long placeId) {
        WeatherPlace place = weatherPlaceMapper.selectOneByCondition(WEATHER_PLACE.USER_ID
                                                                         .eq(userId)
                                                                         .and(WEATHER_PLACE.ID.eq(placeId)));
        if (place != null) {
            return place;
        }

        throw new ResourceNotFoundException();
    }
}
