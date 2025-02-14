package com.weutil.todo.controller;

import com.weutil.common.annotation.UserId;
import com.weutil.common.annotation.UserPermission;
import com.weutil.common.model.SingleListResponse;
import com.weutil.todo.entity.TodolistTask;
import com.weutil.todo.model.CreateTodolistTaskDTO;
import com.weutil.todo.model.TodolistFilter;
import com.weutil.todo.model.TodolistTaskVO;
import com.weutil.todo.model.UpdateTodolistTaskDTO;
import com.weutil.todo.service.TodolistFilterService;
import com.weutil.todo.service.TodolistTaskService;
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
public class TodolistTaskController {
    private final TodolistTaskService todolistTaskService;
    private final TodolistFilterService todolistFilterService;

    /**
     * 创建待办任务
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @date 2024/12/24
     * @since 3.0.0
     */
    @PostMapping("/todo/tasks")
    @UserPermission
    public TodolistTaskVO create(@UserId long userId, @Validated @RequestBody CreateTodolistTaskDTO dto) {
        String name = dto.getName();
        Long projectId = dto.getProjectId();

        return convert(todolistTaskService.create(userId, projectId, name));
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
    private TodolistTaskVO convert(TodolistTask entity) {
        TodolistTaskVO vo = TodolistTaskVO.builder()
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
    @DeleteMapping("/todo/tasks/{id}")
    @UserPermission
    public TodolistTaskVO delete(@UserId long userId, @Min(1) @PathVariable("id") long taskId) {
        todolistTaskService.delete(userId, taskId);
        return TodolistTaskVO.builder().id(taskId).build();
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
    @PutMapping("/todo/tasks/{id}")
    @UserPermission
    public TodolistTaskVO update(@UserId long userId, @Min(1) @PathVariable("id") long taskId, @RequestBody UpdateTodolistTaskDTO dto) {
        todolistTaskService.updateWithDTO(userId, taskId, dto);
        return convert(todolistTaskService.getOrThrowById(userId, taskId));
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
    @GetMapping("/todo/tasks/{id}")
    @UserPermission
    public TodolistTaskVO findById(@UserId long userId, @Min(1) @PathVariable("id") long taskId) {
        return convert(todolistTaskService.getWithRelationsOrThrowById(userId, taskId));
    }

    /**
     * 以过滤器为条件，获取任务列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/24
     * @since 3.0.0
     */
    @GetMapping(value = "/todo/tasks", params = "filter")
    @UserPermission
    public SingleListResponse<TodolistTaskVO> getListByFilter(@UserId long userId, @RequestParam("filter") String filterName) {
        TodolistFilter filter = TodolistFilter.valueOf(filterName.toUpperCase());
        List<TodolistTask> list = todolistFilterService.getTaskListByFilter(userId, filter);

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
    @GetMapping(value = "/todo/tasks", params = "project_id")
    @UserPermission
    public SingleListResponse<TodolistTaskVO> getListByProjectId(@UserId long userId, @RequestParam("project_id") long projectId) {
        List<TodolistTask> items = todolistTaskService.listByProjectId(userId, projectId);

        return new SingleListResponse<>(items.stream().map(this::convert).toList());
    }
}
