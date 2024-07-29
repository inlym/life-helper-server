package com.weutil.reminder.controller;

import com.weutil.common.annotation.UserId;
import com.weutil.common.annotation.UserPermission;
import com.weutil.common.model.SingleListResponse;
import com.weutil.common.validation.group.CreateGroup;
import com.weutil.common.validation.group.UpdateGroup;
import com.weutil.reminder.entity.Project;
import com.weutil.reminder.model.Color;
import com.weutil.reminder.model.ProjectDTO;
import com.weutil.reminder.model.ProjectVO;
import com.weutil.reminder.service.ProjectService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 待办项目控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/28
 * @since 3.0.0
 **/
@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    /**
     * 新增项目
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @date 2024/7/28
     * @since 3.0.0
     */
    @PostMapping("/reminder/projects")
    @UserPermission
    public ProjectVO create(@UserId long userId, @Validated(CreateGroup.class) @RequestBody ProjectDTO dto) {
        Project inserted = Project.builder().userId(userId).name(dto.getName()).color(Color.fromCode(dto.getColorCode())).build();
        if (dto.getFavorite()) {
            inserted.setFavoriteTime(LocalDateTime.now());
        }

        projectService.create(inserted);
        return convert(projectService.findById(userId, inserted.getId()));
    }

    /**
     * 将实体对象转化为视图对象
     *
     * @param entity 实体对象
     *
     * @date 2024/7/28
     * @since 3.0.0
     */
    private ProjectVO convert(Project entity) {
        return ProjectVO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .color(entity.getColor())
                .uncompletedTaskCount(entity.getUncompletedTaskCount())
                .build();
    }

    /**
     * 删除项目
     *
     * @param userId    用户 ID
     * @param projectId 项目 ID
     *
     * @date 2024/7/29
     * @since 3.0.0
     */
    @DeleteMapping("/reminder/projects/{id}")
    @UserPermission
    public ProjectVO delete(@UserId long userId, @Positive @PathVariable("id") long projectId) {
        projectService.delete(userId, projectId);

        return ProjectVO.builder().id(projectId).build();
    }

    /**
     * 修改项目信息
     *
     * @param userId    用户 ID
     * @param projectId 项目 ID
     * @param dto       请求数据
     *
     * @date 2024/7/29
     * @since 3.0.0
     */
    @PutMapping("/reminder/projects/{id}")
    @UserPermission
    public ProjectVO update(@UserId long userId, @Positive @PathVariable("id") long projectId, @Validated(UpdateGroup.class) @RequestBody ProjectDTO dto) {
        projectService.update(userId, projectId, dto);

        return convert(projectService.findById(userId, projectId));
    }

    /**
     * 获取单个项目详情
     *
     * @param userId    用户 ID
     * @param projectId 项目 ID
     *
     * @date 2024/7/29
     * @since 3.0.0
     */
    @GetMapping("/reminder/projects/{id}")
    @UserPermission
    public ProjectVO findOne(@UserId long userId, @Positive @PathVariable("id") long projectId) {
        return convert(projectService.findById(userId, projectId));
    }

    /**
     * 获取项目列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/7/29
     * @since 3.0.0
     */
    @GetMapping("/reminder/projects")
    @UserPermission
    public SingleListResponse<ProjectVO> list(@UserId long userId) {
        List<ProjectVO> list = projectService.list(userId).stream().map(this::convert).toList();
        return new SingleListResponse<>(list);
    }
}
