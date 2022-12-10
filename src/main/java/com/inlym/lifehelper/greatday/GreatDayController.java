package com.inlym.lifehelper.greatday;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.common.validation.SimpleUUID;
import com.inlym.lifehelper.greatday.entity.GreatDay;
import com.inlym.lifehelper.greatday.pojo.CreateOrUpdateGreatDayDTO;
import com.inlym.lifehelper.greatday.pojo.EmojiListResponseVO;
import com.inlym.lifehelper.greatday.pojo.GreatDayListResponseVO;
import com.inlym.lifehelper.greatday.pojo.GreatDayVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@Slf4j
@RequiredArgsConstructor
@Validated
public class GreatDayController {
    private final GreatDayService greatDayService;

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
    public GreatDayVO create(@UserId int userId, @Valid @RequestBody CreateOrUpdateGreatDayDTO dto) {
        GreatDay day = GreatDay
            .builder()
            .userId(userId)
            .name(dto.getName())
            .date(dto.getDate())
            .icon(dto.getIcon())
            .comment(dto.getComment())
            .build();

        return greatDayService.display(greatDayService.create(day));
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
    public GreatDayVO delete(@UserId int userId, @SimpleUUID @PathVariable("id") String id) {
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
    public GreatDayVO update(@UserId int userId, @SimpleUUID @PathVariable("id") String id, @Valid @RequestBody CreateOrUpdateGreatDayDTO dto) {
        GreatDay day = GreatDay
            .builder()
            .userId(userId)
            .dayId(id)
            .name(dto.getName())
            .date(dto.getDate())
            .icon(dto.getIcon())
            .comment(dto.getComment())
            .build();

        return greatDayService.display(greatDayService.update(day));
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
        return greatDayService.display(greatDayService.findOneOrThrow(userId, id));
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
    public GreatDayListResponseVO findAll(@UserId int userId) {
        List<GreatDayVO> list = greatDayService
            .list(userId)
            .stream()
            .map(greatDayService::display)
            .toList();

        return GreatDayListResponseVO
            .builder()
            .list(list)
            .build();
    }

    /**
     * 获取可用的 emoji 列表
     *
     * @since 1.8.0
     */
    @GetMapping("/greatday-icon")
    public EmojiListResponseVO getEmojiList() {
        String[] emojis = {"😀", "🥰", "😛", "🤩", "🥳", "🤓", "😬", "😙", "🤪", "🥺", "🤗"};
        List<String> list = Arrays
            .stream(emojis)
            .toList();

        return EmojiListResponseVO
            .builder()
            .list(list)
            .build();
    }
}
