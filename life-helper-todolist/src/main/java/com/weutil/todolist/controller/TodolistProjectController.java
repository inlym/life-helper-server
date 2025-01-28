package com.weutil.todolist.controller;

import com.weutil.common.annotation.UserId;
import com.weutil.common.annotation.UserPermission;
import com.weutil.common.model.SingleListResponse;
import com.weutil.common.validation.group.CreateGroup;
import com.weutil.todolist.entity.TodolistProject;
import com.weutil.todolist.model.TodolistProjectDTO;
import com.weutil.todolist.model.TodolistProjectVO;
import com.weutil.todolist.service.TodolistProjectService;
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
public class TodolistProjectController {
    private final TodolistProjectService todolistProjectService;

    /**
     * 创建待办项目
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @date 2024/12/13
     * @since 3.0.0
     */
    @PostMapping("/todolist/projects")
    @UserPermission
    public TodolistProjectVO create(@UserId long userId, @Validated(CreateGroup.class) @RequestBody TodolistProjectDTO dto) {
        long projectId = todolistProjectService.createWithDTO(userId, dto);
        return convert(todolistProjectService.getOrThrowById(userId, projectId));
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
    private TodolistProjectVO convert(TodolistProject entity) {
        return TodolistProjectVO.builder()
            .id(entity.getId())
            .name(entity.getName())
            .emoji(entity.getEmoji())
            .color(entity.getColor())
            .favorite(entity.getFavoriteTime() != null)
            .build();
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
    @DeleteMapping("/todolist/projects/{id}")
    @UserPermission
    public TodolistProjectVO delete(@UserId long userId, @Min(1) @PathVariable("id") long projectId) {
        todolistProjectService.delete(userId, projectId);
        return TodolistProjectVO.builder().id(projectId).build();
    }

    /**
     * 修改待办项目信息
     *
     * @param userId    用户 ID
     * @param projectId 待办项目 ID
     * @param dto       请求数据
     *
     * @date 2024/12/26
     * @since 3.0.0
     */
    @PutMapping("/todolist/projects/{id}")
    @UserPermission
    public TodolistProjectVO update(@UserId long userId, @Min(1) @PathVariable("id") long projectId, @RequestBody TodolistProjectDTO dto) {
        todolistProjectService.updateWithDTO(userId, projectId, dto);
        return convert(todolistProjectService.getOrThrowById(userId, projectId));
    }

    /**
     * 获取待办项目列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/13
     * @since 3.0.0
     */
    @GetMapping("/todolist/projects")
    @UserPermission
    public SingleListResponse<TodolistProjectVO> list(@UserId long userId) {
        List<TodolistProjectVO> items = todolistProjectService.list(userId).stream().map(this::convert).toList();
        return new SingleListResponse<>(items);
    }

    /**
     * 查看待办项目信息
     *
     * @param userId    用户 ID
     * @param projectId 待办项目 ID
     *
     * @date 2024/12/26
     * @since 3.0.0
     */
    @GetMapping("/todolist/projects/{id}")
    @UserPermission
    public TodolistProjectVO getOne(@UserId long userId, @Min(1) @PathVariable("id") long projectId) {
        return convert(todolistProjectService.getOrThrowById(userId, projectId));
    }
}
