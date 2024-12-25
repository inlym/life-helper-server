package com.weutil.reminder.service;

import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import com.weutil.reminder.entity.ReminderTask;
import com.weutil.reminder.mapper.ReminderTaskMapper;
import com.weutil.reminder.model.ReminderFilterTaskCount;
import com.weutil.reminder.model.TaskFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.weutil.reminder.entity.table.ReminderTaskTableDef.REMINDER_TASK;

/**
 * 待办任务过滤器服务
 *
 * <h2>说明
 * <p>1. 通过过滤器对任务进行筛选。
 * <p>2. 当前服务仅作“数据查询”。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/24
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ReminderFilterService {
    private final ReminderTaskMapper reminderTaskMapper;

    /**
     * 根据过滤器名称获取任务列表
     *
     * @param userId 用户 ID
     * @param filter 过滤器
     *
     * @date 2024/12/25
     * @since 3.0.0
     */
    public List<ReminderTask> listByFilter(long userId, TaskFilter filter) {
        if (filter == TaskFilter.ALL_UNCOMPLETED) {
            return listByAllUncompletedFilter(userId);
        }
        if (filter == TaskFilter.ALL_COMPLETED) {
            return listByAllCompletedFilter(userId);
        }
        if (filter == TaskFilter.TODAY) {
            return listByTodayFilter(userId);
        }
        if (filter == TaskFilter.EXPIRED) {
            return listByExpiredFilter(userId);
        }
        if (filter == TaskFilter.UNDATED) {
            return listByUndatedFilter(userId);
        }

        return new ArrayList<>();
    }

    /**
     * 以过滤器“所有待办（未完成）”为条件，获取任务列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/24
     * @since 3.0.0
     */
    public List<ReminderTask> listByAllUncompletedFilter(long userId) {
        QueryCondition condition = REMINDER_TASK.USER_ID.eq(userId).and(REMINDER_TASK.COMPLETE_TIME.isNull());

        return reminderTaskMapper.selectListWithRelationsByQuery(QueryWrapper.create().where(condition));
    }

    /**
     * 以过滤器“已完成”为条件，获取任务列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/24
     * @since 3.0.0
     */
    public List<ReminderTask> listByAllCompletedFilter(long userId) {
        QueryCondition condition = REMINDER_TASK.USER_ID.eq(userId).and(REMINDER_TASK.COMPLETE_TIME.isNotNull());

        return reminderTaskMapper.selectListWithRelationsByQuery(QueryWrapper.create().where(condition));
    }

    /**
     * 以过滤器“今天”为条件，获取任务列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/24
     * @since 3.0.0
     */
    public List<ReminderTask> listByTodayFilter(long userId) {
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        QueryCondition condition = REMINDER_TASK.USER_ID.eq(userId).and(REMINDER_TASK.DUE_TIME.between(start, end));

        return reminderTaskMapper.selectListWithRelationsByQuery(QueryWrapper.create().where(condition));
    }

    /**
     * 以过滤器“已过期”为条件，获取任务列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/24
     * @since 3.0.0
     */
    public List<ReminderTask> listByExpiredFilter(long userId) {
        QueryCondition condition = REMINDER_TASK.USER_ID.eq(userId).and(REMINDER_TASK.DUE_TIME.le(LocalDateTime.now()));

        return reminderTaskMapper.selectListWithRelationsByQuery(QueryWrapper.create().where(condition));
    }

    /**
     * 以过滤器“无期限”为条件，获取任务列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/24
     * @since 3.0.0
     */
    public List<ReminderTask> listByUndatedFilter(long userId) {
        QueryCondition condition = REMINDER_TASK.USER_ID.eq(userId).and(REMINDER_TASK.DUE_TIME.isNull());

        return reminderTaskMapper.selectListWithRelationsByQuery(QueryWrapper.create().where(condition));
    }

    /**
     * 以过滤器“未分类”为条件，获取任务列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/24
     * @since 3.0.0
     */
    public List<ReminderTask> listByUnspecifiedFilter(long userId) {
        QueryCondition condition = REMINDER_TASK.USER_ID.eq(userId).and(REMINDER_TASK.PROJECT_ID.eq(0L));

        return reminderTaskMapper.selectListWithRelationsByQuery(QueryWrapper.create().where(condition));
    }

    /**
     * 计算各过滤器的（未完成）任务数
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/24
     * @since 3.0.0
     */
    public ReminderFilterTaskCount count(long userId) {
        return ReminderFilterTaskCount.builder()
            .allUncompleted(reminderTaskMapper.selectCountByCondition(REMINDER_TASK.USER_ID.eq(userId).and(REMINDER_TASK.COMPLETE_TIME.isNull())))
            .allCompleted(reminderTaskMapper.selectCountByCondition(REMINDER_TASK.USER_ID.eq(userId).and(REMINDER_TASK.COMPLETE_TIME.isNotNull())))
            .today(reminderTaskMapper.selectCountByCondition(REMINDER_TASK.USER_ID.eq(userId).and(REMINDER_TASK.COMPLETE_TIME.isNull())
                .and(REMINDER_TASK.DUE_TIME.between(LocalDateTime.of(LocalDate.now(), LocalTime.MIN), LocalDateTime.of(LocalDate.now(), LocalTime.MAX)))))
            .expired(reminderTaskMapper.selectCountByCondition(REMINDER_TASK.USER_ID.eq(userId)
                .and(REMINDER_TASK.COMPLETE_TIME.isNull())
                .and(REMINDER_TASK.DUE_TIME.le(LocalDateTime.now()))))
            .undated(reminderTaskMapper.selectCountByCondition(REMINDER_TASK.USER_ID.eq(userId)
                .and(REMINDER_TASK.COMPLETE_TIME.isNull())
                .and(REMINDER_TASK.DUE_TIME.isNull())))
            .unspecified(reminderTaskMapper.selectCountByCondition(REMINDER_TASK.USER_ID.eq(userId)
                .and(REMINDER_TASK.COMPLETE_TIME.isNull())
                .and(REMINDER_TASK.PROJECT_ID.eq(0L))))
            .build();
    }
}
