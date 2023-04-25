package com.inlym.lifehelper.greatday.service;

import com.inlym.lifehelper.greatday.entity.GreatDay;
import com.inlym.lifehelper.greatday.pojo.GreatDayVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * 纪念日模块 - 数据转换服务
 *
 * <h2>主要用途
 * <p>将模块内的各个数据转换方法集中在这个服务类中。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/4/25
 * @since 2.0.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class GreatDayDataConversionService {
    public GreatDayVO convertToVO(GreatDay day) {
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
