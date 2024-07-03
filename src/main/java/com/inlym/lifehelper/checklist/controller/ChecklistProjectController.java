package com.inlym.lifehelper.checklist.controller;

import com.inlym.lifehelper.checklist.entity.ChecklistProject;
import com.inlym.lifehelper.checklist.model.ChecklistProjectVO;
import com.inlym.lifehelper.checklist.model.SavingProjectDTO;
import com.inlym.lifehelper.checklist.service.ChecklistProjectConverterService;
import com.inlym.lifehelper.checklist.service.ChecklistProjectService;
import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.common.model.CommonListResponse;
import com.inlym.lifehelper.common.validation.group.CreateGroup;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 待办项目控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/3
 * @since 2.3.0
 **/
@RestController
@RequiredArgsConstructor
@Validated
public class ChecklistProjectController {
    private final ChecklistProjectConverterService checklistProjectConverterService;
    private final ChecklistProjectService checklistProjectService;

    /**
     * 新增
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @date 2024/7/3
     * @since 2.3.0
     */
    @PostMapping("/checklist/projects")
    @UserPermission
    public ChecklistProjectVO add(@UserId long userId, @Validated(CreateGroup.class) @RequestBody SavingProjectDTO dto) {
        ChecklistProject entity = checklistProjectConverterService.convertToInsertedEntity(userId, dto);
        checklistProjectService.add(entity);

        return getRefreshVO(userId, entity.getId());
    }

    /**
     * 获取最新的待办项目视图对象
     *
     * @param userId    用户 ID
     * @param projectId 待办项目 ID
     *
     * @date 2024/7/3
     * @since 2.3.0
     */
    private ChecklistProjectVO getRefreshVO(long userId, long projectId) {
        return checklistProjectConverterService.convertToVO(checklistProjectService.findById(userId, projectId));
    }

    /**
     * 删除
     *
     * @param userId    用户 ID
     * @param projectId 待办项目 ID
     *
     * @date 2024/7/3
     * @since 2.3.0
     */
    @DeleteMapping("/checklist/projects/{id}")
    @UserPermission
    public ChecklistProjectVO delete(@UserId long userId, @Positive @PathVariable("id") long projectId) {
        checklistProjectService.delete(userId, projectId);

        // 删除不需要获取最新视图对象，直接返回个 ID 用于前端处理就够了
        return ChecklistProjectVO.builder().id(projectId).build();
    }

    /**
     * 修改
     *
     * @param userId    用户 ID
     * @param projectId 待办项目 ID
     * @param dto       请求数据
     *
     * @date 2024/7/3
     * @since 2.3.0
     */
    @PutMapping("/checklist/projects/{id}")
    @UserPermission
    public ChecklistProjectVO modify(@UserId long userId, @Positive @PathVariable("id") long projectId, @Validated @RequestBody SavingProjectDTO dto) {
        ChecklistProject entity = checklistProjectConverterService.convertToUpdatedEntity(userId, projectId, dto);
        checklistProjectService.update(entity);

        return getRefreshVO(userId, projectId);
    }

    /**
     * 获取列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/7/3
     * @since 2.3.0
     */
    @GetMapping("/checklist/projects")
    @UserPermission
    public CommonListResponse<ChecklistProjectVO> list(@UserId long userId) {
        List<ChecklistProject> projectList = checklistProjectService.list(userId);
        List<ChecklistProjectVO> voList = projectList.stream().map(checklistProjectConverterService::convertToVO).toList();

        return new CommonListResponse<>(voList);
    }
}
