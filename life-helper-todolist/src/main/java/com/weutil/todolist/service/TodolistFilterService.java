package com.weutil.todolist.service;

import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import com.weutil.common.exception.UnpredictableException;
import com.weutil.todolist.entity.TodolistTask;
import com.weutil.todolist.mapper.ReminderTaskMapper;
import com.weutil.todolist.model.TodolistFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.weutil.todolist.entity.table.ReminderTaskTableDef.REMINDER_TASK;

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
public class TodolistFilterService {
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
    public List<TodolistTask> getTaskListByFilter(long userId, TodolistFilter filter) {
        QueryCondition condition = generateCondition(userId, filter);
        return reminderTaskMapper.selectListWithRelationsByQuery(QueryWrapper.create().where(condition));
    }

    /**
     * 根据过滤器名称构建查询条件
     *
     * @param userId 用户 ID
     * @param filter 过滤器
     *
     * @date 2025/01/08
     * @since 3.0.0
     */
    private QueryCondition generateCondition(long userId, TodolistFilter filter) {
        QueryCondition base = REMINDER_TASK.USER_ID.eq(userId);

        if (filter == TodolistFilter.ALL) {
            return base;
        }
        if (filter == TodolistFilter.INBOX) {
            return base.and(REMINDER_TASK.PROJECT_ID.eq(0L));
        }
        if (filter == TodolistFilter.TODAY) {
            LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
            return base.and(REMINDER_TASK.DUE_DATE_TIME.between(start, end));
        }
        if (filter == TodolistFilter.NEXT_SEVEN_DAYS) {
            LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            LocalDateTime end = LocalDateTime.of(LocalDate.now().plusDays(6L), LocalTime.MAX);
            return base.and(REMINDER_TASK.DUE_DATE_TIME.between(start, end));
        }
        if (filter == TodolistFilter.OVERDUE) {
            return base.and(REMINDER_TASK.DUE_DATE_TIME.le(LocalDateTime.now()));
        }
        if (filter == TodolistFilter.NO_DATE) {
            return base.and(REMINDER_TASK.DUE_DATE_TIME.isNull());
        }
        if (filter == TodolistFilter.COMPLETED) {
            return base.and(REMINDER_TASK.COMPLETE_TIME.isNotNull());
        }

        throw new UnpredictableException("ReminderFilter 出现了未处理的枚举值");
    }

    /**
     * 计算过滤器的未完成任务数
     *
     * @param userId 用户 ID
     * @param filter 过滤器
     *
     * @date 2025/01/08
     * @since 3.0.0
     */
    public long countUncompletedTasks(long userId, TodolistFilter filter) {
        QueryCondition condition = generateCondition(userId, filter).and(REMINDER_TASK.COMPLETE_TIME.isNull());
        return reminderTaskMapper.selectCountByCondition(condition);
    }
}
