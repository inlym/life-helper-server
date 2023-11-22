package com.inlym.lifehelper.todo.controller;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.todo.entity.TodoProject;
import com.inlym.lifehelper.todo.model.AddOrEditTodoProjectDTO;
import com.inlym.lifehelper.todo.service.TodoProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 待办事项清单管理控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/10/10
 * @since 2.0.3
 **/
@RestController
@RequiredArgsConstructor
public class TodoProjectController {
    private final TodoProjectService todoProjectService;

    /**
     * 创建项目
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @date 2023/11/22
     * @since 2.0.3
     */
    @PostMapping("/todo/project")
    @UserPermission
    public TodoProject add(@UserId int userId, @Valid @RequestBody AddOrEditTodoProjectDTO dto) {
        String name = dto.getName();
        return todoProjectService.add(userId, name);
    }

    /**
     * 删除项目
     *
     * @param userId    用户 ID
     * @param projectId 项目 ID
     *
     * @date 2023/11/22
     * @since 2.0.3
     */
    @DeleteMapping("/todo/project/{id}")
    @UserPermission
    public TodoProject delete(@UserId int userId, @PathVariable("id") long projectId) {
        todoProjectService.delete(userId, projectId);

        return TodoProject
            .builder()
            .id(projectId)
            .build();
    }

    /**
     * 编辑项目信息
     *
     * @param userId    用户 ID
     * @param projectId 项目 ID
     * @param dto       请求数据
     *
     * @date 2023/11/22
     * @since 2.0.3
     */
    @PutMapping("/todo/project/{id}")
    @UserPermission
    public TodoProject edit(@UserId int userId, @PathVariable("id") long projectId, @Valid @RequestBody AddOrEditTodoProjectDTO dto) {
        return todoProjectService.editName(userId, projectId, dto.getName());
    }

    /**
     * 获取项目列表
     *
     * @param userId 用户 ID
     *
     * @date 2023/11/22
     * @since 2.0.3
     */
    @GetMapping("/todo/projects")
    @UserPermission
    public List<TodoProject> list(@UserId int userId) {
        return todoProjectService.list(userId);
    }
}
