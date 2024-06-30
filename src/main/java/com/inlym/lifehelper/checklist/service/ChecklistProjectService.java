package com.inlym.lifehelper.checklist.service;

import static com.inlym.lifehelper.checklist.entity.table.ChecklistProjectTableDef.CHECKLIST_PROJECT;

import com.inlym.lifehelper.checklist.entity.ChecklistProject;
import com.inlym.lifehelper.checklist.exception.ChecklistProjectNotFoundException;
import com.inlym.lifehelper.checklist.mapper.ChecklistProjectMapper;
import com.mybatisflex.core.query.QueryCondition;
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
   * 创建待办清单
   *
   * @param project 待办清单实体
   * @date 2024/7/1
   * @since 2.3.0
   */
  public void createProject(ChecklistProject project) {
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
  public void deleteProject(long userId, long projectId) {
    int i = checklistProjectMapper.deleteByCondition(generateCondition(userId, projectId));
    log.trace("[ChecklistProject] userId={}, projectId={}, 删除{}行", userId, projectId, i);
  }
}
