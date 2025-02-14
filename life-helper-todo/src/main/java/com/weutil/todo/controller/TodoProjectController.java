package com.weutil.todo.controller;

import com.weutil.common.annotation.UserId;
import com.weutil.common.annotation.UserPermission;
import com.weutil.common.model.SingleListResponse;
import com.weutil.common.validation.group.CreateGroup;
import com.weutil.todo.entity.TodoProject;
import com.weutil.todo.model.TodoProjectDTO;
import com.weutil.todo.model.TodoProjectVO;
import com.weutil.todo.service.TodoProjectService;
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
public class TodoProjectController {
    private final TodoProjectService todoProjectService;

    /**
     * 创建待办项目
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @date 2024/12/13
     * @since 3.0.0
     */
    @PostMapping("/todo/projects")
    @UserPermission
    public TodoProjectVO create(@UserId long userId, @Validated(CreateGroup.class) @RequestBody TodoProjectDTO dto) {
        long projectId = todoProjectService.createWithDTO(userId, dto);
        return convert(todoProjectService.getOrThrowById(userId, projectId));
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
    private TodoProjectVO convert(TodoProject entity) {
        return TodoProjectVO.builder()
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
    @DeleteMapping("/todo/projects/{id}")
    @UserPermission
    public TodoProjectVO delete(@UserId long userId, @Min(1) @PathVariable("id") long projectId) {
        todoProjectService.delete(userId, projectId);
        return TodoProjectVO.builder().id(projectId).build();
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
    @PutMapping("/todo/projects/{id}")
    @UserPermission
    public TodoProjectVO update(@UserId long userId, @Min(1) @PathVariable("id") long projectId, @RequestBody TodoProjectDTO dto) {
        todoProjectService.updateWithDTO(userId, projectId, dto);
        return convert(todoProjectService.getOrThrowById(userId, projectId));
    }

    /**
     * 获取待办项目列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/13
     * @since 3.0.0
     */
    @GetMapping("/todo/projects")
    @UserPermission
    public SingleListResponse<TodoProjectVO> list(@UserId long userId) {
        List<TodoProjectVO> items = todoProjectService.list(userId).stream().map(this::convert).toList();
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
    @GetMapping("/todo/projects/{id}")
    @UserPermission
    public TodoProjectVO getOne(@UserId long userId, @Min(1) @PathVariable("id") long projectId) {
        return convert(todoProjectService.getOrThrowById(userId, projectId));
    }
}
