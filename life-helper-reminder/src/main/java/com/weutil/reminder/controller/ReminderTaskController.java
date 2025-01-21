package com.weutil.reminder.controller;

import com.weutil.common.annotation.UserId;
import com.weutil.common.annotation.UserPermission;
import com.weutil.common.model.SingleListResponse;
import com.weutil.reminder.entity.ReminderTask;
import com.weutil.reminder.model.CreateReminderTaskDTO;
import com.weutil.reminder.model.ReminderFilter;
import com.weutil.reminder.model.ReminderTaskVO;
import com.weutil.reminder.model.UpdateReminderTaskDTO;
import com.weutil.reminder.service.ReminderFilterService;
import com.weutil.reminder.service.ReminderTaskService;
import jakarta.validation.constraints.Min;
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
        ReminderTaskVO vo = ReminderTaskVO.builder()
            .id(entity.getId())
            .projectId(entity.getProjectId())
            .name(entity.getName())
            .content(entity.getContent())
            .completeTime(entity.getCompleteTime())
            .dueDate(entity.getDueDate())
            .dueTime(entity.getDueTime())
            .priority(entity.getPriority())
            .build();

        if (entity.getProject() != null) {
            vo.setProjectName(entity.getProject().getName());
        }

        return vo;
    }

    /**
     * 删除一条待办任务
     *
     * @param userId 用户 ID
     * @param taskId 待办任务 ID
     *
     * @date 2024/12/26
     * @since 3.0.0
     */
    @DeleteMapping("/reminder/tasks/{id}")
    @UserPermission
    public ReminderTaskVO delete(@UserId long userId, @Min(1) @PathVariable("id") long taskId) {
        reminderTaskService.delete(userId, taskId);
        return ReminderTaskVO.builder().id(taskId).build();
    }

    /**
     * 更新一条待办任务
     *
     * @param userId 用户 ID
     * @param taskId 待办任务 ID
     * @param dto    请求数据
     *
     * @date 2024/12/26
     * @since 3.0.0
     */
    @PutMapping("/reminder/tasks/{id}")
    @UserPermission
    public ReminderTaskVO update(@UserId long userId, @Min(1) @PathVariable("id") long taskId, @RequestBody UpdateReminderTaskDTO dto) {
        reminderTaskService.updateWithDTO(userId, taskId, dto);
        return convert(reminderTaskService.getOrThrowById(userId, taskId));
    }

    /**
     * 查看一条待办任务的详情
     *
     * @param userId 用户 ID
     * @param taskId 待办任务 ID
     *
     * @date 2024/12/26
     * @since 3.0.0
     */
    @GetMapping("/reminder/tasks/{id}")
    @UserPermission
    public ReminderTaskVO findById(@UserId long userId, @Min(1) @PathVariable("id") long taskId) {
        return convert(reminderTaskService.getWithRelationsOrThrowById(userId, taskId));
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
    public SingleListResponse<ReminderTaskVO> getListByFilter(@UserId long userId, @RequestParam("filter") String filterName) {
        ReminderFilter filter = ReminderFilter.valueOf(filterName.toUpperCase());
        List<ReminderTask> list = reminderFilterService.getTaskListByFilter(userId, filter);

        return new SingleListResponse<>(list.stream().map(this::convert).toList());
    }

    /**
     * 以项目 ID 为条件，获取任务列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/24
     * @since 3.0.0
     */
    @GetMapping(value = "/reminder/tasks", params = "project_id")
    @UserPermission
    public SingleListResponse<ReminderTaskVO> getListByProjectId(@UserId long userId, @RequestParam("project_id") long projectId) {
        List<ReminderTask> items = reminderTaskService.listByProjectId(userId, projectId);

        return new SingleListResponse<>(items.stream().map(this::convert).toList());
    }
}
