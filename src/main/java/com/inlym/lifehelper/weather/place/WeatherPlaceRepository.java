package com.inlym.lifehelper.weather.place;

import com.inlym.lifehelper.common.base.aliyun.ots.widecolumn.WideColumnExecutor;
import com.inlym.lifehelper.weather.place.entity.WeatherPlace;
import com.inlym.lifehelper.weather.place.exception.WeatherPlaceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * 天气地点存储库
 *
 * <h2>主要用途
 * <p>仅封装 {@link com.inlym.lifehelper.weather.place.entity.WeatherPlace} 实体的增删改查，不处理业务逻辑。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/22
 * @since 1.5.0
 **/
@Repository
@Slf4j
@RequiredArgsConstructor
public class WeatherPlaceRepository {
    private final WideColumnExecutor wideColumnExecutor;

    /**
     * 新增
     *
     * @param place 天气地点
     *
     * @since 1.5.0
     */
    public WeatherPlace create(WeatherPlace place) {
        WeatherPlace wp = new WeatherPlace();
        BeanUtils.copyProperties(place, wp);

        wp.setCreateTime(LocalDateTime.now());

        return wideColumnExecutor.create(wp);
    }

    /**
     * 删除
     *
     * @param userId  用户 ID
     * @param placeId 天气地点 ID
     *
     * @since 1.5.0
     */
    public void delete(int userId, String placeId) {
        WeatherPlace place = WeatherPlace
            .builder()
            .userId(userId)
            .placeId(placeId)
            .build();

        wideColumnExecutor.delete(place);
    }

    /**
     * 获取所有记录
     *
     * @param userId 用户 ID
     *
     * @since 1.5.0
     */
    public List<WeatherPlace> list(int userId) {
        WeatherPlace place = WeatherPlace
            .builder()
            .userId(userId)
            .build();

        List<WeatherPlace> list = wideColumnExecutor.findAll(place, WeatherPlace.class);
        if (list.size() > 0) {
            list.sort(Comparator
                .comparing(WeatherPlace::getCreateTime)
                .reversed());
        }

        return list;
    }

    /**
     * 统计记录行数
     *
     * @param userId 用户 ID
     *
     * @since 1.5.0
     */
    public int count(int userId) {
        return list(userId).size();
    }

    /**
     * 查找指定天气地点，若未找到则报错
     *
     * @param userId  用户 ID
     * @param placeId 天气地点 ID
     *
     * @since 1.5.0
     */
    public WeatherPlace findOneOrElseThrow(int userId, String placeId) {
        WeatherPlace placeSearch = WeatherPlace
            .builder()
            .userId(userId)
            .placeId(placeId)
            .build();

        WeatherPlace place = wideColumnExecutor.findOne(placeSearch, WeatherPlace.class);

        if (place != null) {
            return place;
        }

        // 查不到结果则报错
        throw WeatherPlaceNotFoundException.create(userId, placeId);
    }
}
