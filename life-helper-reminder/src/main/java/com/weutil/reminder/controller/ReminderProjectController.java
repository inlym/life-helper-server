package com.weutil.reminder.controller;

import com.weutil.common.annotation.UserId;
import com.weutil.common.annotation.UserPermission;
import com.weutil.common.model.SingleListResponse;
import com.weutil.common.validation.group.CreateGroup;
import com.weutil.reminder.entity.ReminderProject;
import com.weutil.reminder.model.ReminderProjectVO;
import com.weutil.reminder.model.SaveReminderProjectDTO;
import com.weutil.reminder.service.ReminderProjectService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 待办项目控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/13
 * @since 3.0.0
 **/
@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
public class ReminderProjectController {
    private final ReminderProjectService reminderProjectService;

    /**
     * 创建待办项目
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @date 2024/12/13
     * @since 3.0.0
     */
    @PostMapping("/reminder/projects")
    @UserPermission
    public ReminderProjectVO create(@UserId long userId, @Validated(CreateGroup.class) @RequestBody SaveReminderProjectDTO dto) {
        String name = dto.getName();
        ReminderProject entity = reminderProjectService.create(userId, name);

        return convert(entity);
    }

    /**
     * 将实体对象转化为视图对象
     *
     * @param entity 实体对象
     *
     * @return 视图对象
     * @date 2024/12/13
     * @since 3.0.0
     */
    private ReminderProjectVO convert(ReminderProject entity) {
        return ReminderProjectVO.builder().id(entity.getId()).name(entity.getName()).uncompletedTaskCount(entity.getUncompletedTaskCount()).build();
    }

    /**
     * 获取待办项目列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/13
     * @since 3.0.0
     */
    @GetMapping("/reminder/projects")
    @UserPermission
    public SingleListResponse<ReminderProjectVO> list(@UserId long userId) {
        List<ReminderProjectVO> items = reminderProjectService.list(userId).stream().map(this::convert).toList();
        return new SingleListResponse<>(items);
    }

    /**
     * 删除一个指定的待办项目
     *
     * @param userId    用户 ID
     * @param projectId 待办项目 ID
     *
     * @date 2024/12/13
     * @since 3.0.0
     */
    @DeleteMapping("/reminder/projects/{project_id}")
    @UserPermission
    public ReminderProjectVO delete(@UserId long userId, @Min(1) @PathVariable("project_id") long projectId) {
        reminderProjectService.delete(userId, projectId);
        return ReminderProjectVO.builder().id(projectId).build();
    }
}
