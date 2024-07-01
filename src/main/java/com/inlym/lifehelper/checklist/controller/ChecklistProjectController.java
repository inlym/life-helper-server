package com.inlym.lifehelper.checklist.controller;

import com.inlym.lifehelper.checklist.entity.ChecklistProject;
import com.inlym.lifehelper.checklist.model.ChecklistProjectVO;
import com.inlym.lifehelper.checklist.model.SavingProjectDTO;
import com.inlym.lifehelper.checklist.service.ChecklistProjectService;
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
 */
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class ChecklistProjectController {
  private final ChecklistProjectService checklistProjectService;

  /**
   * 新增待办清单项目
   *
   * @param userId 用户 ID
   * @param dto 请求数据
   * @date 2024/6/11
   * @since 2.3.0
   */
  @PostMapping("/checklist/projects")
  @UserPermission
  public ChecklistProjectVO createProject(
      @UserId long userId, @Validated(CreateGroup.class) @RequestBody SavingProjectDTO dto) {
    log.trace("[createProject] userId={}, dto={}", userId, dto);
    ChecklistProject project =
        ChecklistProject.builder().userId(userId).name(dto.getName()).color(dto.getColor()).build();
    checklistProjectService.create(project);

    return convert(checklistProjectService.findOneById(userId, project.getId()));
  }

  /**
   * 将实体对象转化为视图对象
   *
   * @param entity 实体对象
   * @date 2024/7/1
   * @since 2.3.0
   */
  private ChecklistProjectVO convert(ChecklistProject entity) {
    return ChecklistProjectVO.builder()
        .id(entity.getId())
        .createTime(entity.getCreateTime())
        .name(entity.getName())
        .color(entity.getColor())
        .pinTime(entity.getPinTime())
        .taskCount(entity.getTaskCount())
        .build();
  }

  /**
   * 删除待办清单项目
   *
   * @param userId 用户 ID
   * @param projectId 待办清单项目 ID
   * @date 2024/6/11
   * @since 2.3.0
   */
  @DeleteMapping("/checklist/projects/{id}")
  @UserPermission
  public ChecklistProjectVO deleteProject(
      @UserId long userId, @Positive @PathVariable("id") long projectId) {
    log.trace("[deleteProject] userId={}, id={}", userId, projectId);
    checklistProjectService.delete(userId, projectId);
    return ChecklistProjectVO.builder().id(projectId).build();
  }

  /**
   * 修改待办清单项目
   *
   * @param userId 用户 ID
   * @param dto 请求数据
   * @param id 待办清单项目 ID
   * @date 2024/6/11
   * @since 2.3.0
   */
  @PutMapping("/checklist/projects/{id}")
  @UserPermission
  public ChecklistProjectVO updateProject(
      @UserId long userId,
      @Validated(UpdateGroup.class) @RequestBody SavingProjectDTO dto,
      @Positive @PathVariable("id") long id) {
    log.trace("[updateProject] userId={}, dto={}, id={}", userId, dto, id);
    ChecklistProject updated =
        ChecklistProject.builder().id(id).name(dto.getName()).color(dto.getColor()).build();
    checklistProjectService.update(updated);

    return convert(checklistProjectService.findOneById(userId, id));
  }

  /**
   * 操作「置顶」项目
   *
   * @param userId 用户 ID
   * @param id 待办清单项目 ID
   * @date 2024/6/11
   * @since 2.3.0
   */
  @PutMapping("/checklist/projects/{id}/pin")
  @UserPermission
  public ChecklistProjectVO pinProject(@UserId long userId, @Positive @PathVariable("id") long id) {
    log.trace("[pinProject] userId={}, id={}", userId, id);
    checklistProjectService.pin(userId, id);

    return convert(checklistProjectService.findOneById(userId, id));
  }

  /**
   * 操作「取消置顶」项目
   *
   * @param userId 用户 ID
   * @param id 待办清单项目 ID
   * @date 2024/6/11
   * @since 2.3.0
   */
  @PutMapping("/checklist/projects/{id}/unpin")
  @UserPermission
  public ChecklistProjectVO unpinProject(
      @UserId long userId, @Positive @PathVariable("id") long id) {
    log.trace("[unpinProject] userId={}, id={}", userId, id);
    checklistProjectService.unpin(userId, id);

    return convert(checklistProjectService.findOneById(userId, id));
  }
}
