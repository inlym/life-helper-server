package com.inlym.lifehelper.greatday.service;

import com.inlym.lifehelper.common.base.aliyun.ots.widecolumn.WideColumnExecutor;
import com.inlym.lifehelper.greatday.entity.GreatDay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 纪念日存储库
 *
 * <h2>主要用途
 * <p>管理纪念日模块数据的增删改查。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/7
 * @since 1.8.0
 **/
@Repository
@Slf4j
@RequiredArgsConstructor
public class GreatDayRepository {
    private final WideColumnExecutor wideColumnExecutor;

    /**
     * 新增
     *
     * @param greatDay 纪念日实体
     * @since 1.8.0
     */
    public GreatDay create(GreatDay greatDay) {
        return wideColumnExecutor.create(greatDay);
    }

    /**
     * 更新（编辑）
     *
     * @param greatDay 纪念日实体
     * @since 1.8.0
     */
    public void update(GreatDay greatDay) {
        wideColumnExecutor.update(greatDay);
    }

    /**
     * 删除
     *
     * @param userId 用户 ID
     * @param dayId  纪念日 ID
     * @since 1.8.0
     */
    public void delete(int userId, String dayId) {
        GreatDay greatDay = GreatDay
                .builder()
                .userId(userId)
                .dayId(dayId)
                .build();

        wideColumnExecutor.delete(greatDay);
    }

    /**
     * 通过用户 ID 查找所有记录
     *
     * @param userId 用户 ID
     * @since 1.8.0
     */
    public List<GreatDay> findAll(int userId) {
        GreatDay greatDay = GreatDay
                .builder()
                .userId(userId)
                .build();

        return wideColumnExecutor.findAll(greatDay, GreatDay.class);
    }

    /**
     * 通过 ID 查找记录
     *
     * @param userId 用户 ID
     * @param dayId  纪念日 ID
     * @since 1.8.0
     */
    public GreatDay findOne(int userId, String dayId) {
        GreatDay greatDay = GreatDay
                .builder()
                .userId(userId)
                .dayId(dayId)
                .build();

        return wideColumnExecutor.findOne(greatDay, GreatDay.class);
    }
}
