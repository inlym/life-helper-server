package com.inlym.lifehelper.greatday.service;

import com.inlym.lifehelper.greatday.entity.GreatDay;
import com.inlym.lifehelper.greatday.pojo.GreatDayVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * 纪念日模块视图服务
 *
 * <h2>主要用途
 * <p>对接控制器和服务层，做数据转换。
 *
 * <h2>使用说明
 * <p>只允许在 `XxxViewService` 出现对 VO/DTO 的处理，不允许在其他服务类中出现。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/5/26
 * @since 2.0.0
 **/
@Service
@RequiredArgsConstructor
public class GreatDayViewService {
    /**
     * 将实体对象转化为视图对象
     *
     * @param day 纪念日实体对象
     *
     * @return 转化后的视图对象
     *
     * @date 2023/5/26
     * @since 2.0.0
     */
    public GreatDayVO convert(GreatDay day) {
        return GreatDayVO
            .builder()
            .id(day.getDayId())
            .name(day.getName())
            .date(day.getDate())
            .icon(day.getIcon())
            .days(day
                      .getDate()
                      .toEpochDay() - LocalDate
                .now()
                .toEpochDay())
            .build();
    }
}
