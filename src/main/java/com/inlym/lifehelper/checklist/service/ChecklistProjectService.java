package com.inlym.lifehelper.checklist.service;

import com.inlym.lifehelper.checklist.entity.ChecklistProject;
import com.inlym.lifehelper.checklist.event.ChecklistTaskEvent;
import com.inlym.lifehelper.checklist.exception.ChecklistProjectAccessDeniedException;
import com.inlym.lifehelper.checklist.exception.ChecklistProjectLimitExceeded;
import com.inlym.lifehelper.checklist.exception.ChecklistProjectNotEmptyException;
import com.inlym.lifehelper.checklist.exception.ChecklistProjectNotFoundException;
import com.inlym.lifehelper.checklist.mapper.ChecklistProjectMapper;
import com.mybatisflex.core.query.QueryCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.inlym.lifehelper.checklist.entity.table.ChecklistProjectTableDef.CHECKLIST_PROJECT;

/**
 * 待办项目服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/2
 * @since 2.3.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ChecklistProjectService {
    /** 最大可创建的项目数 */
    private static final long MAX_NUM = 100;
    private final ChecklistProjectMapper checklistProjectMapper;
    private final ChecklistSharedCountingService checklistSharedCountingService;

    /**
     * 新增
     *
     * @param entity 实体数据
     *
     * @date 2024/7/2
     * @since 2.3.0
     */
    public void add(ChecklistProject entity) {
        // 检查是否达到数量上限
        long num = checklistSharedCountingService.countProjects(entity.getUserId());
        if (num >= MAX_NUM) {
            throw new ChecklistProjectLimitExceeded();
        }

        checklistProjectMapper.insertSelective(entity);
    }

    /**
     * 删除待办项目
     *
     * @param userId    用户 ID
     * @param projectId 待办项目 ID
     *
     * @date 2024/7/2
     * @since 2.3.0
     */
    public void delete(long userId, long projectId) {
        ChecklistProject project = findById(userId, projectId);
        if (project != null) {
            // 检查当前项目下的“未完成”任务数，不为0则不允许删除
            long num = checklistSharedCountingService.countUncompletedTasksForProject(projectId);
            if (num > 0) {
                throw new ChecklistProjectNotEmptyException();
            }

            // 全部校验通过后，最后执行删除
            checklistProjectMapper.deleteById(projectId);
        }
    }

    /**
     * 通过 ID 查找待办项目
     *
     * @param userId    用户 ID
     * @param projectId 待办项目 ID
     *
     * @date 2024/7/2
     * @since 2.3.0
     */
    public ChecklistProject findById(long userId, long projectId) {
        ChecklistProject entity = checklistProjectMapper.selectOneById(projectId);

        // 通过逐渐 ID 查找未找到情况
        if (entity == null) {
            throw new ChecklistProjectNotFoundException();
        }
        // 资源存在，但是不属于对应用户，当前仅打印日志，后期开发纳入安全体系预警机制
        if (!Objects.equals(entity.getUserId(), userId)) {
            log.trace("[疑似伪造请求攻击] userId={} 的用户尝试访问了不属于自己的 ChecklistProject(id={}) .", userId, projectId);
            throw new ChecklistProjectAccessDeniedException();
        }

        // 所有检验通过，返回查找到的记录
        return entity;
    }

    /**
     * 更新信息
     *
     * @param entity 待办项目实体对象
     *
     * @date 2024/7/2
     * @since 2.3.0
     */
    public void update(ChecklistProject entity) {
        checklistProjectMapper.update(entity);
    }

    /**
     * 获取指定用户的待办项目列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/7/2
     * @since 2.3.0
     */
    public List<ChecklistProject> list(long userId) {
        QueryCondition condition = CHECKLIST_PROJECT.USER_ID.eq(userId);
        return checklistProjectMapper.selectListByCondition(condition);
    }

    /**
     * 异步监听待办任务系列事件
     *
     * <h3>处理策略
     * <p>[Good] 每次触发都是重新计算当前数值。
     * <p>[Bad] 根据子事件类型，然后进行 +1, -1 等计算。
     *
     * @param event 待办任务事件
     */
    @Async
    @EventListener(ChecklistTaskEvent.class)
    public void listenToChecklistTaskEvent(ChecklistTaskEvent event) {
        long num = checklistSharedCountingService.countUncompletedTasksForProject(event.getProjectId());
        ChecklistProject updated = ChecklistProject.builder().id(event.getProjectId()).uncompletedTaskCount(num).build();
        checklistProjectMapper.update(updated);
    }
}
