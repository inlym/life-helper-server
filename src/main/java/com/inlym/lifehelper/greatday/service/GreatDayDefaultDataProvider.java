package com.inlym.lifehelper.greatday.service;

import com.inlym.lifehelper.greatday.pojo.GreatDayVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * 纪念日模块默认数据服务
 *
 * <h2>主要用途
 * <p>为空数据用户提供初始化数据，避免展示空列表。
 *
 * <h2>使用说明
 * <p>（1）用户有数据情况，使用用户已有的数据，当用户无数据时，使用该模块输出展示，避免展示空列表。
 * <p>（2）直接生成了用于“视图层”使用的数据，当前服务应该直接在 `...ViewService` 中调用。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/4/25
 * @since 2.0.0
 **/
@Service
@RequiredArgsConstructor
public class GreatDayDefaultDataProvider {
    private static final GreatDayVO DEFAULT_DAY1 = GreatDayVO
        .builder()
        .id(1L)
        .name("小鸣助手上线")
        .date(LocalDate.of(2019, 7, 6))
        .icon("\uD83C\uDF89")
        .systemCreated(true)
        .build();

    private static final GreatDayVO DEFAULT_DAY2 = GreatDayVO
        .builder()
        .id(2L)
        .name("2024年元旦")
        .date(LocalDate.of(2024, 1, 1))
        .icon("\uD83D\uDE80")
        .systemCreated(true)
        .build();

    /**
     * 获取默认列表
     *
     * <h2>使用场景
     * <p>用于获取用户所有纪念日数据时作为缺省值。
     *
     * @date 2023/5/31
     * @since 2.0.1
     */
    public List<GreatDayVO> getDefaultList() {
        return List.of(DEFAULT_DAY1, DEFAULT_DAY2);
    }

    /**
     * 根据 ID 返回数据
     *
     * <h2>使用场景
     * <p>当列表为默认数据后，进入到详情页需要根据 ID 获取数据，适用于该情况返回数据。
     *
     * @param id 纪念日 ID
     *
     * @date 2023/5/31
     * @since 2.0.1
     */
    public GreatDayVO getById(long id) {
        if (id == DEFAULT_DAY1.getId()) {
            return DEFAULT_DAY1;
        } else if (id == DEFAULT_DAY2.getId()) {
            return DEFAULT_DAY2;
        } else {
            return null;
        }
    }
}
