package com.weutil.reminder.controller;

import com.weutil.common.annotation.UserId;
import com.weutil.common.annotation.UserPermission;
import com.weutil.common.model.SingleListResponse;
import com.weutil.reminder.entity.ReminderTask;
import com.weutil.reminder.model.CreateReminderTaskDTO;
import com.weutil.reminder.model.ReminderFilterTaskCount;
import com.weutil.reminder.model.ReminderTaskVO;
import com.weutil.reminder.model.TaskFilter;
import com.weutil.reminder.service.ReminderFilterService;
import com.weutil.reminder.service.ReminderTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 待办任务控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/24
 * @since 3.0.0
 **/
@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
public class ReminderTaskController {
    private final ReminderTaskService reminderTaskService;
    private final ReminderFilterService reminderFilterService;

    /**
     * 创建待办任务
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @date 2024/12/24
     * @since 3.0.0
     */
    @PostMapping("/reminder/tasks")
    @UserPermission
    public ReminderTaskVO create(@UserId long userId, @Validated @RequestBody CreateReminderTaskDTO dto) {
        String name = dto.getName();
        Long projectId = dto.getProjectId();

        return convert(reminderTaskService.create(userId, projectId, name));
    }

    /**
     * 将实体对象转化为视图对象
     *
     * @param entity 实体对象
     *
     * @return 视图对象
     * @date 2024/12/24
     * @since 3.0.0
     */
    private ReminderTaskVO convert(ReminderTask entity) {
        return ReminderTaskVO.builder()
            .id(entity.getId())
            .projectId(entity.getProjectId())
            .name(entity.getName())
            .content(entity.getContent())
            .dueTime(entity.getDueTime())
            .build();
    }

    /**
     * 以过滤器为条件，获取任务列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/24
     * @since 3.0.0
     */
    @GetMapping(value = "/reminder/tasks", params = "filter")
    @UserPermission
    public SingleListResponse<ReminderTaskVO> getByFilter(@UserId long userId, @RequestParam("filter") TaskFilter filter) {
        List<ReminderTask> list = reminderFilterService.listByFilter(userId, filter);

        return new SingleListResponse<>(list.stream().map(this::convert).toList());
    }

    /**
     * 计算各过滤器的（未完成）任务数
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/25
     * @since 3.0.0
     */
    @GetMapping("/reminder/filters/count")
    @UserPermission
    public ReminderFilterTaskCount countFilter(@UserId long userId) {
        return reminderFilterService.count(userId);
    }
}
