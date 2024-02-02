package com.inlym.lifehelper.greatday.service;

import com.inlym.lifehelper.greatday.entity.GreatDay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * 纪念日模块服务
 *
 * <h2>主要用途
 * <p>处理该模块业务逻辑层面上的增删改查。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/5/27
 * @since 2.0.0
 **/
@Service
@RequiredArgsConstructor
public class GreatDayService {
    private final GreatDayRepository repository;

    /**
     * 添加一条新的纪念日
     *
     * @param day 纪念日实体对象
     *
     * @return 附加了自增 ID 的实体对象
     *
     * @date 2023/5/27
     * @since 2.0.0
     */
    public GreatDay create(GreatDay day) {
        // 必要性检查和处理
        if (day.getDayId() != null) {
            day.setDayId(null);
        }

        // 业务逻辑处理：
        // 之后引入 VIP 后会限定最大创建条数，本次不限定

        return repository.create(day);
    }

    /**
     * 更新操作
     *
     * <h2>使用备忘
     * <p>只有不为 null 的字段会被修改，为 null 的字段不做任何处理。
     *
     * @param day 纪念日实体对象
     *
     * @date 2023/5/27
     * @since 2.0.0
     */
    public void update(GreatDay day) {
        repository.update(day);
    }

    /**
     * 删除一条纪念日
     *
     * @param userId 用户 ID
     * @param dayId  纪念日 ID
     *
     * @date 2023/5/27
     * @since 2.0.0
     */
    public void delete(long userId, long dayId) {
        // （待补充）删除前的判断

        repository.delete(userId, dayId);
    }

    /**
     * 获取指定用户的所有记录
     *
     * @param userId 用户 ID
     *
     * @return 属于该用户的纪念日列表
     *
     * @date 2023/5/27
     * @since 2.0.0
     */
    public List<GreatDay> findAll(long userId) {
        return repository.findAll(userId);
    }

    /**
     * 根据 ID 查找
     *
     * @param userId 用户 ID
     * @param dayId  纪念日 ID
     *
     * @return 实体对象或为 null
     *
     * @date 2023/5/27
     * @since 2.0.0
     */
    public GreatDay findOne(long userId, long dayId) {
        return repository.findOne(userId, dayId);
    }

    /**
     * 计算天数间隔
     *
     * <h2>说明
     * <p>若返回值 >0 ，说明目标日期在未来。
     * <p>若返回值 <0 ，说明目标日期已过去。
     *
     * @param day 目标日期
     *
     * @date 2023/5/31
     * @since 2.0.2
     */
    public long calcDaysInterval(LocalDate day) {
        return day.toEpochDay() - LocalDate
            .now()
            .toEpochDay();
    }
}
