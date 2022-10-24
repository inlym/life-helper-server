package com.inlym.lifehelper.weather.weatherplace;

import com.github.pagehelper.PageHelper;
import com.inlym.lifehelper.location.LocationService;
import com.inlym.lifehelper.location.pojo.AddressComponent;
import com.inlym.lifehelper.weather.data.WeatherDataService;
import com.inlym.lifehelper.weather.data.pojo.WeatherNow;
import com.inlym.lifehelper.weather.weatherplace.entity.WeatherPlace;
import com.inlym.lifehelper.weather.weatherplace.mapper.WeatherPlaceMapper;
import com.inlym.lifehelper.weather.weatherplace.pojo.WeatherPlaceWithWeatherNowBO;
import com.inlym.lifehelper.weather.weatherplace.pojo.WeixinChooseLocationDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 天气地点服务类
 *
 * @author inlym
 * @date 2022-02-13 20:48
 **/
@Service
@Slf4j
public class WeatherPlaceService2 {
    private final WeatherPlaceMapper weatherPlaceMapper;

    private final LocationService locationService;

    private final WeatherDataService weatherDataService;

    public WeatherPlaceService2(WeatherPlaceMapper weatherPlaceMapper, LocationService locationService, WeatherDataService weatherDataService) {
        this.weatherPlaceMapper = weatherPlaceMapper;
        this.locationService = locationService;
        this.weatherDataService = weatherDataService;
    }

    /**
     * 将天气地点实体转换为业务对象
     *
     * @param place 天气地点实体
     *
     * @since 1.0.0
     */
    public WeatherPlaceWithWeatherNowBO convertWeatherPlace(WeatherPlace place) {
        WeatherPlaceWithWeatherNowBO bo = new WeatherPlaceWithWeatherNowBO();
        WeatherNow weatherNow = weatherDataService.getWeatherNow(place.getLongitude(), place.getLatitude());
        BeanUtils.copyProperties(weatherNow, bo);

        bo.setId(place.getId());
        bo.setName(place.getName());
        bo.setRegion(place.getCity() + place.getDistrict());

        return bo;
    }

    /**
     * 将天气地点实体转换为业务对象（异步）
     *
     * @param place 天气地点实体
     *
     * @since 1.0.0
     */
    @Async
    public CompletableFuture<WeatherPlaceWithWeatherNowBO> convertWeatherPlaceAsync(WeatherPlace place) {
        return CompletableFuture.completedFuture(convertWeatherPlace(place));
    }

    /**
     * 新增一个天气地点
     *
     * @param dto 前端传输的请求数据
     *
     * @since 1.0.0
     */
    public WeatherPlaceWithWeatherNowBO add(int userId, WeixinChooseLocationDTO dto) {
        AddressComponent ac = locationService.reverseGeocoding(dto.getLongitude(), dto.getLatitude());

        WeatherPlace place = new WeatherPlace();
        place.setUserId(userId);
        place.setName(dto.getName());
        place.setAddress(dto.getAddress());
        place.setLongitude(dto.getLongitude());
        place.setLatitude(dto.getLatitude());
        place.setProvince(ac.getProvince());
        place.setCity(ac.getCity());
        place.setDistrict(ac.getDistrict());

        weatherPlaceMapper.insert(place);

        return convertWeatherPlace(place);
    }

    /**
     * 根据 ID 查询天气地点
     *
     * @param userId 用户 ID
     * @param id     主键 ID
     *
     * @since 1.0.0
     */
    public WeatherPlace findById(int userId, int id) {
        return weatherPlaceMapper.findById(userId, id);
    }

    /**
     * 获取指定用户所属的天气地点列表
     *
     * @param userId 用户 ID
     *
     * @return 天气地点列表
     *
     * @apiNote 默认只展示前10条
     * @since 1.0.0
     */
    public List<WeatherPlace> list(int userId) {
        // 只展示前 10 条数据
        PageHelper.startPage(1, 10);
        return weatherPlaceMapper.list(userId);
    }

    /**
     * 获取转化后可直接输出的天气地点列表
     *
     * @param userId 用户 ID
     *
     * @since 1.0.0
     */
    @SneakyThrows
    public List<WeatherPlaceWithWeatherNowBO> getConvertedWeatherPlaceList(int userId) {
        List<WeatherPlace> list = list(userId);
        log.info("天气地点列表：{}", list);
        List<CompletableFuture<WeatherPlaceWithWeatherNowBO>> list2 = list
            .stream()
            .map(this::convertWeatherPlaceAsync)
            .toList();

        return list2
            .stream()
            .map(CompletableFuture::join)
            .toList();
    }

    /**
     * 删除一条天气地点
     *
     * @param userId 用户 ID
     * @param id     主键 ID
     */
    public void delete(int userId, int id) {
        weatherPlaceMapper.delete(userId, id);
    }
}
