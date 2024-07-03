package com.inlym.lifehelper.checklist.service;

import com.inlym.lifehelper.checklist.constant.TaskStatus;
import com.inlym.lifehelper.checklist.mapper.ChecklistProjectMapper;
import com.inlym.lifehelper.checklist.mapper.ChecklistTagMapper;
import com.inlym.lifehelper.checklist.mapper.ChecklistTaskMapper;
import com.mybatisflex.core.query.QueryCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.inlym.lifehelper.checklist.entity.table.ChecklistProjectTableDef.CHECKLIST_PROJECT;
import static com.inlym.lifehelper.checklist.entity.table.ChecklistTagTableDef.CHECKLIST_TAG;
import static com.inlym.lifehelper.checklist.entity.table.ChecklistTaskTableDef.CHECKLIST_TASK;

/**
 * 待办清单模块 - 共用计数服务
 *
 * <h2>主要用途
 * <p>在各自“服务”内，需要计算其他实体的数量，引用对方服务，可能造成依赖的“循环引用”，因此将“计数”事项剥离出来，都统一放置在当前服务中处理。
 *
 * <h2>附加说明
 * <p>为方便处理，在当前服务中直接调用了 {@code Mapper}，该操作不符合规范，特此说明。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/3
 * @since 2.3.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ChecklistSharedCountingService {
    private final ChecklistProjectMapper checklistProjectMapper;
    private final ChecklistTaskMapper checklistTaskMapper;
    private final ChecklistTagMapper checklistTagMapper;

    /**
     * 计算用户的项目数
     *
     * @param userId 用户 ID
     *
     * @date 2024/7/3
     * @since 2.3.0
     */
    public long countProjects(long userId) {
        QueryCondition condition = CHECKLIST_PROJECT.USER_ID.eq(userId);
        return checklistProjectMapper.selectCountByCondition(condition);
    }

    /**
     * 计算用户的标签数
     *
     * @param userId 用户 ID
     *
     * @date 2024/7/3
     * @since 2.3.0
     */
    public long countTags(long userId) {
        QueryCondition condition = CHECKLIST_TAG.USER_ID.eq(userId);
        return checklistTagMapper.selectCountByCondition(condition);
    }

    /**
     * 计算指定项目的总任务数
     *
     * @param projectId 待办项目 ID
     *
     * @date 2024/7/3
     * @since 2.3.0
     */
    public long countTasksForProject(long projectId) {
        QueryCondition condition = CHECKLIST_TASK.PROJECT_ID.eq(projectId);
        return checklistTaskMapper.selectCountByCondition(condition);
    }

    /**
     * 计算指定项目未完成的任务数
     *
     * @param projectId 待办项目 ID
     *
     * @date 2024/7/3
     * @since 2.3.0
     */
    public long countUncompletedTasksForProject(long projectId) {
        QueryCondition condition = CHECKLIST_TASK.PROJECT_ID.eq(projectId).and(CHECKLIST_TASK.STATUS.notIn(TaskStatus.COMPLETED));
        return checklistTaskMapper.selectCountByCondition(condition);
    }
}
