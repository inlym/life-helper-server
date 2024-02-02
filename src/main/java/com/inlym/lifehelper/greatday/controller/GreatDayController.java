package com.inlym.lifehelper.greatday.controller;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.common.model.CommonListResponse;
import com.inlym.lifehelper.greatday.pojo.GreatDayVO;
import com.inlym.lifehelper.greatday.pojo.SaveGreatDayDTO;
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

    private final GreatDayViewService greatDayViewService;

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
    public GreatDayVO create(@UserId long userId, @Valid @RequestBody SaveGreatDayDTO dto) {
        return greatDayViewService.create(userId, dto);
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
    public GreatDayVO delete(@UserId long userId, @PathVariable("id") Long id) {
        return greatDayViewService.delete(userId, id);
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
    public GreatDayVO update(@UserId long userId, @PathVariable("id") long id, @Valid @RequestBody SaveGreatDayDTO dto) {
        return greatDayViewService.update(userId, id, dto);
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
    public GreatDayVO findOne(@UserId long userId, @PathVariable("id") long id) {
        return greatDayViewService.findOne(userId, id);
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
    public CommonListResponse<GreatDayVO> findAll(@UserId long userId) {
        return greatDayViewService.findAll(userId);
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
