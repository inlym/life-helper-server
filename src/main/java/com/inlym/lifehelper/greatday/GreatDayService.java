package com.inlym.lifehelper.greatday;

import com.inlym.lifehelper.greatday.entity.GreatDay;
import com.inlym.lifehelper.greatday.exception.GreatDayNotFoundException;
import com.inlym.lifehelper.greatday.pojo.GreatDayVO;
import com.inlym.lifehelper.greatday.repository.GreatDayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * 纪念日服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/7
 * @since 1.8.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class GreatDayService {
    private final GreatDayRepository greatDayRepository;

    /**
     * 获取实体对象的展示视图
     *
     * @param day 实体对象
     *
     * @since 1.8.0
     */
    public GreatDayVO display(GreatDay day) {
        return GreatDayVO
            .builder()
            .id(day.getDayId())
            .name(day.getName())
            .date(day.getDate())
            .icon(day.getIcon())
            .comment(day.getComment())
            .days(day
                .getDate()
                .toEpochDay() - LocalDate
                .now()
                .toEpochDay())
            .build();
    }

    /**
     * 检查对应记录是否存在（若不存在则抛出异常）
     *
     * @param userId 用户 ID
     * @param dayId  纪念日 ID
     *
     * @since 1.8.0
     */
    private void existOrThrow(int userId, String dayId) {
        GreatDay day = greatDayRepository.findOne(userId, dayId);
        if (day == null) {
            throw new GreatDayNotFoundException();
        }
    }

    /**
     * 创建
     *
     * @param day 纪念日实体对象
     *
     * @since 1.8.0
     */
    public GreatDay create(GreatDay day) {
        return greatDayRepository.create(day);
    }

    /**
     * 删除一条记录
     *
     * @param userId 用户 ID
     * @param dayId  纪念日 ID
     *
     * @since 1.8.0
     */
    public void delete(int userId, String dayId) {
        existOrThrow(userId, dayId);
        greatDayRepository.delete(userId, dayId);
    }

    /**
     * 更新纪念日
     *
     * @param day 纪念日实体对象
     *
     * @since 1.8.0
     */
    public GreatDay update(GreatDay day) {
        existOrThrow(day.getUserId(), day.getDayId());
        greatDayRepository.update(day);

        return day;
    }

    /**
     * 获取所有的纪念日
     *
     * @param userId 用户 ID
     *
     * @since 1.8.0
     */
    public List<GreatDay> list(int userId) {
        return greatDayRepository.findAll(userId);
    }

    /**
     * 查找唯一记录
     *
     * @param userId 用户 ID
     * @param dayId  纪念日 ID
     *
     * @since 1.8.0
     */
    public GreatDay findOneOrThrow(int userId, String dayId) {
        GreatDay day = greatDayRepository.findOne(userId, dayId);
        if (day == null) {
            throw new GreatDayNotFoundException();
        }

        return day;
    }
}
