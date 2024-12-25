package com.weutil.reminder.service;

import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import com.weutil.reminder.entity.ReminderProject;
import com.weutil.reminder.event.ReminderTaskCreatedEvent;
import com.weutil.reminder.exception.ReminderProjectNotAllowedToDeleteException;
import com.weutil.reminder.exception.ReminderProjectNotFoundException;
import com.weutil.reminder.mapper.ReminderProjectMapper;
import com.weutil.reminder.mapper.ReminderTaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.weutil.reminder.entity.table.ReminderProjectTableDef.REMINDER_PROJECT;
import static com.weutil.reminder.entity.table.ReminderTaskTableDef.REMINDER_TASK;

/**
 * 待办项目服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/12
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ReminderProjectService {
    private final ReminderProjectMapper reminderProjectMapper;
    private final ReminderTaskMapper reminderTaskMapper;

    /**
     * 创建待办项目
     *
     * @param userId 用户 ID
     * @param name   项目名称
     *
     * @return 插入后重新查询获取的待办项目实体对象
     * @date 2024/12/12
     * @since 3.0.0
     */
    public ReminderProject create(long userId, String name) {
        ReminderProject entity = ReminderProject.builder().userId(userId).name(name).build();
        reminderProjectMapper.insertSelective(entity);

        return getOrThrowById(userId, entity.getId());
    }

    /**
     * 根据 ID 获取实体对象，如果未找到（包含不所属于对应用户）则抛出异常
     *
     * @param userId    用户 ID
     * @param projectId 待办项目 ID
     *
     * @return 待办项目实体对象
     * @date 2024/12/13
     * @since 3.0.0
     */
    private ReminderProject getOrThrowById(long userId, long projectId) {
        ReminderProject entity = reminderProjectMapper.selectOneById(projectId);
        if (entity != null && entity.getUserId() == userId) {
            return entity;
        }

        throw new ReminderProjectNotFoundException(projectId, userId);
    }

    /**
     * 获取指定用户的待办项目列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/12
     * @since 3.0.0
     */
    public List<ReminderProject> list(long userId) {
        QueryCondition condition = REMINDER_PROJECT.USER_ID.eq(userId);
        QueryWrapper queryWrapper = QueryWrapper.create().where(condition).orderBy(REMINDER_PROJECT.ID, false);

        return reminderProjectMapper.selectListByQuery(queryWrapper);
    }

    /**
     * 删除待办项目
     *
     * @param userId    用户 ID
     * @param projectId 项目 ID
     *
     * @date 2024/12/12
     * @since 3.0.0
     */
    public void delete(long userId, long projectId) {
        ReminderProject entity = getOrThrowById(userId, projectId);

        // 下方逐个检查不允许删除的条件，并抛出异常
        // 本期内仅以下1个条件（2024.12.13）
        if (entity.getUncompletedTaskCount() > 0) {
            throw new ReminderProjectNotAllowedToDeleteException("请先将当前项目下的未完成任务删除或移动至其他项目后，再操作删除");
        }

        // 完成所有检查，进行“删除”操作
        reminderProjectMapper.deleteById(entity.getId());
    }

    /**
     * 监听待办任务创建事件
     *
     * <h4>备注
     * <p>此处需同步处理，避免异步处理时间差问题（前端提交成功后，会立刻更新项目列表）。
     *
     * @param event 待办任务创建事件
     *
     * @date 2024/12/24
     * @since 3.0.0
     */
    @EventListener(ReminderTaskCreatedEvent.class)
    public void handleReminderTaskCreatedEvent(ReminderTaskCreatedEvent event) {
        Long projectId = event.getTask().getProjectId();
        if (projectId != null && projectId > 0) {
            // 处理策略：不是直接 +1，而是重新算一遍再赋值
            long count = calcUncompletedTaskCount(projectId);
            ReminderProject updated = ReminderProject.builder().id(projectId).uncompletedTaskCount(count).build();
            reminderProjectMapper.update(updated);
        }
    }

    /**
     * 计算指定项目的未完成任务数
     *
     * @param projectId 项目 ID
     *
     * @date 2024/12/24
     * @since 3.0.0
     */
    public long calcUncompletedTaskCount(long projectId) {
        QueryCondition condition = REMINDER_TASK.PROJECT_ID.eq(projectId).and(REMINDER_TASK.COMPLETE_TIME.isNull());
        return reminderTaskMapper.selectCountByCondition(condition);
    }
}
