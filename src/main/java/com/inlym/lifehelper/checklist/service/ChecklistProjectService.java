package com.inlym.lifehelper.checklist.service;

import static com.inlym.lifehelper.checklist.entity.table.ChecklistProjectTableDef.CHECKLIST_PROJECT;

import com.inlym.lifehelper.checklist.entity.ChecklistProject;
import com.inlym.lifehelper.checklist.exception.ChecklistProjectNotFoundException;
import com.inlym.lifehelper.checklist.mapper.ChecklistProjectMapper;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.util.UpdateEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 待办清单服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/1
 * @since 2.3.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ChecklistProjectService {
  private final ChecklistProjectMapper checklistProjectMapper;

  /**
   * 创建待办清单
   *
   * @param project 待办清单实体
   * @date 2024/7/1
   * @since 2.3.0
   */
  public void create(ChecklistProject project) {
    checklistProjectMapper.insertSelective(project);
  }

  /**
   * 删除待办清单
   *
   * @param userId 用户 ID
   * @param projectId 待办清单 ID
   * @date 2024/7/1
   * @since 2.3.0
   */
  public void delete(long userId, long projectId) {
    int i = checklistProjectMapper.deleteByCondition(generateCondition(userId, projectId));
    log.trace("[ChecklistProject] userId={}, projectId={}, 删除{}行", userId, projectId, i);
  }

  /**
   * 生成查询条件
   *
   * @param userId 用户 ID
   * @param projectId 待办清单 ID
   * @date 2024/7/1
   * @since 2.3.0
   */
  private QueryCondition generateCondition(long userId, long projectId) {
    return CHECKLIST_PROJECT.USER_ID.eq(userId).and(CHECKLIST_PROJECT.ID.eq(projectId));
  }

  /**
   * 更新待办清单
   *
   * @param project 待办清单实体
   * @date 2024/7/1
   * @since 2.3.0
   */
  public void update(ChecklistProject project) {
    checklistProjectMapper.update(project);
  }

  /**
   * 获取指定用户的待办清单列表
   *
   * @param userId 用户 ID
   * @date 2024/7/1
   * @since 2.3.0
   */
  public List<ChecklistProject> list(long userId) {
    return checklistProjectMapper.selectListByCondition(CHECKLIST_PROJECT.USER_ID.eq(userId));
  }

  /**
   * “置顶”操作
   *
   * @param userId 用户 ID
   * @param projectId 待办清单 ID
   * @date 2024/7/1
   * @since 2.3.0
   */
  public void pin(long userId, long projectId) {
    ChecklistProject project = findOneById(userId, projectId);
    if (project != null) {
      ChecklistProject updated =
          ChecklistProject.builder().id(projectId).pinTime(LocalDateTime.now()).build();
      checklistProjectMapper.update(updated);
    }
  }

  /**
   * 查找待办清单
   *
   * @param userId 用户 ID
   * @param projectId 待办清单 ID
   * @date 2024/7/1
   * @since 2.3.0
   */
  public ChecklistProject findOneById(long userId, long projectId) {
    ChecklistProject result =
        checklistProjectMapper.selectOneByCondition(generateCondition(userId, projectId));
    if (result != null) {
      return result;
    }
    throw new ChecklistProjectNotFoundException();
  }

  /**
   * “取消置顶”操作
   *
   * @param userId 用户 ID
   * @param projectId 待办清单 ID
   * @date 2024/7/1
   * @since 2.3.0
   */
  public void unpin(long userId, long projectId) {
    ChecklistProject project = findOneById(userId, projectId);
    if (project != null) {
      ChecklistProject updated = UpdateEntity.of(ChecklistProject.class, projectId);
      updated.setPinTime(null);
      checklistProjectMapper.update(updated);
    }
  }
}
