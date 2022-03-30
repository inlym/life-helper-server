package com.inlym.lifehelper.weather.weatherplace;

import com.github.pagehelper.PageHelper;
import com.inlym.lifehelper.location.LocationService;
import com.inlym.lifehelper.location.pojo.AddressComponent;
import com.inlym.lifehelper.weather.weatherplace.entity.WeatherPlace;
import com.inlym.lifehelper.weather.weatherplace.mapper.WeatherPlaceMapper;
import com.inlym.lifehelper.weather.weatherplace.pojo.WeixinChooseLocationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 天气地点服务类
 *
 * @author inlym
 * @date 2022-02-13 20:48
 **/
@Service
@Slf4j
public class WeatherPlaceService {
    private final WeatherPlaceMapper weatherPlaceMapper;

    private final LocationService locationService;

    public WeatherPlaceService(WeatherPlaceMapper weatherPlaceMapper, LocationService locationService) {
        this.weatherPlaceMapper = weatherPlaceMapper;
        this.locationService = locationService;
    }

    /**
     * 新增一个天气地点
     *
     * @param dto 前端传输的请求数据
     */
    public WeatherPlace add(int userId, WeixinChooseLocationDTO dto) {
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

        return place;
    }

    /**
     * 获取指定用户所属的天气地点列表
     *
     * @param userId 用户 ID
     *
     * @return 天气地点列表
     *
     * @apiNote 默认只展示前10条
     */
    public List<WeatherPlace> list(int userId) {
        // 只展示前 10 条数据
        PageHelper.startPage(1, 10);
        return weatherPlaceMapper.list(userId);
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
