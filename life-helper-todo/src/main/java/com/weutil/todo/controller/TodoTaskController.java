package com.weutil.todo.controller;

import com.weutil.common.annotation.UserId;
import com.weutil.common.annotation.UserPermission;
import com.weutil.common.model.SingleListResponse;
import com.weutil.todo.entity.TodoTask;
import com.weutil.todo.model.CreateTodoTaskDTO;
import com.weutil.todo.model.TodoFilter;
import com.weutil.todo.model.TodoTaskVO;
import com.weutil.todo.model.UpdateTodoTaskDTO;
import com.weutil.todo.service.TodoFilterService;
import com.weutil.todo.service.TodoTaskService;
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
public class TodoTaskController {
    private final TodoTaskService todoTaskService;
    private final TodoFilterService todoFilterService;

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
    public TodoTaskVO create(@UserId long userId, @Validated @RequestBody CreateTodoTaskDTO dto) {
        String name = dto.getName();
        Long projectId = dto.getProjectId();

        return convert(todoTaskService.create(userId, projectId, name));
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
    private TodoTaskVO convert(TodoTask entity) {
        TodoTaskVO vo = TodoTaskVO.builder()
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
    public TodoTaskVO delete(@UserId long userId, @Min(1) @PathVariable("id") long taskId) {
        todoTaskService.delete(userId, taskId);
        return TodoTaskVO.builder().id(taskId).build();
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
    public TodoTaskVO update(@UserId long userId, @Min(1) @PathVariable("id") long taskId, @RequestBody UpdateTodoTaskDTO dto) {
        todoTaskService.updateWithDTO(userId, taskId, dto);
        return convert(todoTaskService.getOrThrowById(userId, taskId));
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
    public TodoTaskVO findById(@UserId long userId, @Min(1) @PathVariable("id") long taskId) {
        return convert(todoTaskService.getWithRelationsOrThrowById(userId, taskId));
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
    public SingleListResponse<TodoTaskVO> getListByFilter(@UserId long userId, @RequestParam("filter") String filterName) {
        TodoFilter filter = TodoFilter.valueOf(filterName.toUpperCase());
        List<TodoTask> list = todoFilterService.getTaskListByFilter(userId, filter);

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
    public SingleListResponse<TodoTaskVO> getListByProjectId(@UserId long userId, @RequestParam("project_id") long projectId) {
        List<TodoTask> items = todoTaskService.listByProjectId(userId, projectId);

        return new SingleListResponse<>(items.stream().map(this::convert).toList());
    }
}
