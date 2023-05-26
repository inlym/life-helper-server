package com.inlym.lifehelper.greatday.service;

import com.inlym.lifehelper.greatday.entity.GreatDay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 纪念日模块默认数据服务
 *
 * <h2>主要用途
 * <p>为空数据用户提供初始化数据。
 *
 * <h2>使用说明
 * <p>用户有数据情况，使用用户已有的数据，当用户无数据时，使用该模块输出展示，避免展示空列表。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/4/25
 * @since 2.0.0
 **/
@Service
@RequiredArgsConstructor
public class GreatDayDefaultDataProvider {
    /**
     * 获取默认数据
     *
     * @date 2023/4/25
     * @since 2.0.0
     */
    public List<GreatDay> getDefaultData() {
        GreatDay day1 = GreatDay
            .builder()
            .name("小鸣助手上线")
            .date(LocalDate.of(2019, 7, 6))
            .icon("\uD83C\uDF89")
            .build();

        GreatDay day2 = GreatDay
            .builder()
            .name("2024年元旦")
            .date(LocalDate.of(2024, 1, 1))
            .icon("\uD83D\uDE80")
            .build();

        List<GreatDay> list = new ArrayList<>();
        list.add(day1);
        list.add(day2);

        return list;
    }
}
