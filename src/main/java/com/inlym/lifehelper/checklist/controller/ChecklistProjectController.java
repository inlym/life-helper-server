package com.inlym.lifehelper.checklist.controller;

import com.inlym.lifehelper.checklist.model.ChecklistProjectVO;
import com.inlym.lifehelper.checklist.model.SavingProjectDTO;
import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.common.validation.group.CreateGroup;
import com.inlym.lifehelper.common.validation.group.UpdateGroup;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 待办清单「项目」控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/11
 * @since 2.3.0
 **/
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class ChecklistProjectController {
    /**
     * 新增待办清单项目
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @date 2024/6/11
     * @since 2.3.0
     */
    @PostMapping("/checklist/projects")
    @UserPermission
    public ChecklistProjectVO createProject(@UserId long userId, @Validated(CreateGroup.class) @RequestBody SavingProjectDTO dto) {
        log.debug("[createProject] userId={}, dto={}", userId, dto);
        return null;
    }

    /**
     * 删除待办清单项目
     *
     * @param userId 用户 ID
     * @param id     待办清单项目 ID
     *
     * @date 2024/6/11
     * @since 2.3.0
     */
    @DeleteMapping("/checklist/projects/{id}")
    @UserPermission
    public ChecklistProjectVO deleteProject(@UserId long userId, @Positive @PathVariable("id") long id) {
        log.debug("[deleteProject] userId={}, id={}", userId, id);
        return null;
    }

    /**
     * 修改待办清单项目
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     * @param id     待办清单项目 ID
     *
     * @date 2024/6/11
     * @since 2.3.0
     */
    @PutMapping("/checklist/projects/{id}")
    @UserPermission
    public ChecklistProjectVO updateProject(@UserId long userId, @Validated(UpdateGroup.class) @RequestBody SavingProjectDTO dto, @Positive @PathVariable("id"
    ) long id) {
        log.debug("[updateProject] userId={}, dto={}, id={}", userId, dto, id);
        return null;
    }

    /**
     * 操作「置顶」项目
     *
     * @param userId 用户 ID
     * @param id     待办清单项目 ID
     *
     * @date 2024/6/11
     * @since 2.3.0
     */
    @PutMapping("/checklist/projects/{id}/pin")
    @UserPermission
    public ChecklistProjectVO pinProject(@UserId long userId, @Positive @PathVariable("id") long id) {
        log.debug("[pinProject] userId={}, id={}", userId, id);
        return null;
    }

    /**
     * 操作「取消置顶」项目
     *
     * @param userId 用户 ID
     * @param id     待办清单项目 ID
     *
     * @date 2024/6/11
     * @since 2.3.0
     */
    @PutMapping("/checklist/projects/{id}/unpin")
    @UserPermission
    public ChecklistProjectVO unpinProject(@UserId long userId, @Positive @PathVariable("id") long id) {
        log.debug("[unpinProject] userId={}, id={}", userId, id);
        return null;
    }
}
