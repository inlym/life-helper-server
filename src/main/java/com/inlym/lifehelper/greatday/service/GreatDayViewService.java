package com.inlym.lifehelper.greatday.service;

import com.inlym.lifehelper.common.model.CommonListResponse;
import com.inlym.lifehelper.greatday.entity.GreatDay;
import com.inlym.lifehelper.greatday.pojo.GreatDayVO;
import com.inlym.lifehelper.greatday.pojo.SaveGreatDayDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 纪念日模块视图服务
 *
 * <h2>主要用途
 * <p>对接控制器和服务层，做数据转换，主要为了解决「数据如何展示」的问题。
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
    private final GreatDayService greatDayService;

    private final GreatDayDefaultDataProvider greatDayDefaultDataProvider;

    /**
     * 创建
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @date 2023/5/27
     * @since 2.0.0
     */
    public GreatDayVO create(int userId, SaveGreatDayDTO dto) {
        GreatDay day = GreatDay
            .builder()
            .userId(userId)
            .name(dto.getName())
            .date(dto.getDate())
            .icon(dto.getIcon())
            .build();

        return convert(greatDayService.create(day));
    }

    /**
     * 删除操作
     *
     * @param userId 用户 ID
     * @param dayId  纪念日 ID
     *
     * @date 2023/5/27
     * @since 2.0.0
     */
    public GreatDayVO delete(int userId, Long dayId) {
        greatDayService.delete(userId, dayId);
        return GreatDayVO
            .builder()
            .id(dayId)
            .build();
    }

    /**
     * 编辑操作
     *
     * @param userId 用户 ID
     * @param dayId  纪念日 ID
     * @param dto    请求数据
     *
     * @date 2023/5/27
     * @since 2.0.0
     */
    public GreatDayVO update(int userId, long dayId, SaveGreatDayDTO dto) {
        GreatDay day = GreatDay
            .builder()
            .userId(userId)
            .dayId(dayId)
            .name(dto.getName())
            .date(dto.getDate())
            .icon(dto.getIcon())
            .build();

        greatDayService.update(day);
        return convert(day);
    }

    /**
     * 查看某一条的详情
     *
     * @param userId 用户 ID
     * @param dayId  纪念日 ID
     *
     * @date 2023/5/27
     * @since 2.0.0
     */
    public GreatDayVO findOne(int userId, long dayId) {
        // 优先从默认数据找，因为大多数用户数据为空
        GreatDayVO vo = greatDayDefaultDataProvider.getById(dayId);
        if (vo != null) {
            return vo;
        } else {
            return convert(greatDayService.findOne(userId, dayId));
        }
    }

    /**
     * 获取用户所有的纪念日列表
     *
     * @param userId 用户 ID
     *
     * @date 2023/5/27
     * @since 2.0.0
     */
    public CommonListResponse<GreatDayVO> findAll(int userId) {
        List<GreatDay> list = greatDayService.findAll(userId);

        if (list.size() == 0) {
            // 如果用户自己的数据为空，则给用户展示默认数据
            return new CommonListResponse<>(greatDayDefaultDataProvider.getDefaultList());
        } else {
            // 正常有数据情况，做一下数据的格式转换后返回
            List<GreatDayVO> voList = list
                .stream()
                .map(this::convert)
                .toList();

            return new CommonListResponse<>(voList);
        }
    }

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
    private GreatDayVO convert(GreatDay day) {
        return GreatDayVO
            .builder()
            .id(day.getDayId())
            .name(day.getName())
            .date(day.getDate())
            .icon(day.getIcon())
            .days(greatDayService.calcDaysInterval(day.getDate()))
            .build();
    }
}
