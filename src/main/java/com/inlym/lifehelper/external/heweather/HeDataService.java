package com.inlym.lifehelper.external.heweather;

import com.inlym.lifehelper.external.heweather.pojo.HeCityLookupResponse;
import com.inlym.lifehelper.weather.weatherplace.pojo.HeCity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 和风天气数据服务
 *
 * <h2>说明
 * <p>将从 HTTP 请求获取的数据做二次处理，成为可以内部使用的有效数据。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/3/26
 **/
@Service
public class HeDataService {
    private final HeHttpService heHttpService;

    public HeDataService(HeHttpService heHttpService) {
        this.heHttpService = heHttpService;
    }

    /**
     * 查询和风天气城市
     *
     * @param location 需要查询地区的名称，支持文字、以英文逗号分隔的经度,纬度坐标（十进制，最多支持小数点后两位）、LocationID 或 Adcode（仅限中国城市）
     *
     * @since 1.0.0
     */
    public HeCity[] searchCities(String location) {
        HeCityLookupResponse data = heHttpService.searchCities(location);

        String SUCCESS_CODE = "200";

        if (!SUCCESS_CODE.equals(data.getCode())) {
            return new HeCity[0];
        }

        HeCity[] cities = new HeCity[data.getLocation().length];
        for (int i = 0; i < data.getLocation().length; i++) {
            HeCity city = new HeCity();
            BeanUtils.copyProperties(data.getLocation()[i], city);
            cities[i] = city;
        }

        return cities;
    }
}
