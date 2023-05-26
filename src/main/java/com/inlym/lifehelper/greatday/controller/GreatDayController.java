package com.inlym.lifehelper.greatday.controller;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.common.model.CommonListResponse;
import com.inlym.lifehelper.common.validation.SimpleUUID;
import com.inlym.lifehelper.greatday.entity.GreatDay;
import com.inlym.lifehelper.greatday.pojo.GreatDayVO;
import com.inlym.lifehelper.greatday.pojo.SaveGreatDayDTO;
import com.inlym.lifehelper.greatday.service.GreatDayDefaultDataProvider;
import com.inlym.lifehelper.greatday.service.GreatDayService;
import com.inlym.lifehelper.greatday.service.GreatDayViewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 纪念日模块控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/7
 * @since 1.8.0
 **/
@RestController
@RequiredArgsConstructor
@Validated
public class GreatDayController {
    private final GreatDayService greatDayService;

    private final GreatDayViewService greatDayViewService;

    private final GreatDayDefaultDataProvider greatDayDefaultDataProvider;

    /**
     * 新增
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @since 1.8.0
     */
    @PostMapping("/greatday")
    @UserPermission
    public GreatDayVO create(@UserId int userId, @Valid @RequestBody SaveGreatDayDTO dto) {
        GreatDay day = GreatDay
            .builder()
            .userId(userId)
            .name(dto.getName())
            .date(dto.getDate())
            .icon(dto.getIcon())
            .build();

        return greatDayViewService.convert(greatDayService.create(day));
    }

    /**
     * 删除
     *
     * @param userId 用户 ID
     * @param id     纪念日 ID
     *
     * @since 1.8.0
     */
    @DeleteMapping("/greatday/{id}")
    @UserPermission
    public GreatDayVO delete(@UserId int userId, @PathVariable("id") Long id) {
        greatDayService.delete(userId, id);
        return GreatDayVO
            .builder()
            .id(id)
            .build();
    }

    /**
     * 更新
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @since 1.8.0
     */
    @PutMapping("/greatday/{id}")
    @UserPermission
    public GreatDayVO update(@UserId int userId, @SimpleUUID @PathVariable("id") String id, @Valid @RequestBody SaveGreatDayDTO dto) {
        GreatDay day = GreatDay
            .builder()
            .userId(userId)
            .dayId(999L)
            .name(dto.getName())
            .date(dto.getDate())
            .icon(dto.getIcon())
            .build();

        return greatDayViewService.convert(greatDayService.update(day));
    }

    /**
     * 获取单个详情
     *
     * @param userId 用户 ID
     * @param id     纪念日 ID
     *
     * @since 1.8.0
     */
    @GetMapping("/greatday/{id}")
    @UserPermission
    public GreatDayVO findOne(@UserId int userId, @SimpleUUID @PathVariable("id") String id) {
        return greatDayViewService.convert(greatDayService.findOneOrThrow(userId, id));
    }

    /**
     * 获取列表
     *
     * @param userId 用户 ID
     *
     * @since 1.8.0
     */
    @GetMapping("/greatdays")
    @UserPermission
    public CommonListResponse<GreatDayVO> findAll(@UserId int userId) {
        List<GreatDay> list1 = greatDayService.list(userId);
        List<GreatDay> list2 = greatDayDefaultDataProvider.getDefaultData();

        List<GreatDayVO> list = (list1.size() != 0 ? list1 : list2)
            .stream()
            .map(greatDayViewService::convert)
            .toList();

        return new CommonListResponse<>(list);
    }

    /**
     * 获取可用的 emoji 列表
     *
     * @since 1.8.0
     */
    @GetMapping("/greatday-icon")
    public CommonListResponse<String> getEmojiList() {
        String[] emojis = {"😀", "🥰", "😛", "🤩", "🥳", "🤓", "😬", "😙", "🤪", "🥺", "🤗"};
        List<String> list = Arrays
            .stream(emojis)
            .toList();

        return new CommonListResponse<>(list);
    }
}
