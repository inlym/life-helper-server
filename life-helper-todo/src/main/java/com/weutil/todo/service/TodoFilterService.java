package com.weutil.todo.service;

import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import com.weutil.common.exception.UnpredictableException;
import com.weutil.todo.entity.TodoTask;
import com.weutil.todo.mapper.TodoTaskMapper;
import com.weutil.todo.model.TodoFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.weutil.todo.entity.table.TodoTaskTableDef.TODO_TASK;

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
public class TodoFilterService {
    private final TodoTaskMapper todoTaskMapper;

    /**
     * 根据过滤器名称获取任务列表
     *
     * @param userId 用户 ID
     * @param filter 过滤器
     *
     * @date 2024/12/25
     * @since 3.0.0
     */
    public List<TodoTask> getTaskListByFilter(long userId, TodoFilter filter) {
        QueryCondition condition = generateCondition(userId, filter);
        return todoTaskMapper.selectListWithRelationsByQuery(QueryWrapper.create().where(condition));
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
    private QueryCondition generateCondition(long userId, TodoFilter filter) {
        QueryCondition base = TODO_TASK.USER_ID.eq(userId);

        if (filter == TodoFilter.ALL) {
            return base;
        }
        if (filter == TodoFilter.INBOX) {
            return base.and(TODO_TASK.PROJECT_ID.eq(0L));
        }
        if (filter == TodoFilter.TODAY) {
            LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
            return base.and(TODO_TASK.DUE_DATE_TIME.between(start, end));
        }
        if (filter == TodoFilter.NEXT_SEVEN_DAYS) {
            LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            LocalDateTime end = LocalDateTime.of(LocalDate.now().plusDays(6L), LocalTime.MAX);
            return base.and(TODO_TASK.DUE_DATE_TIME.between(start, end));
        }
        if (filter == TodoFilter.OVERDUE) {
            return base.and(TODO_TASK.DUE_DATE_TIME.le(LocalDateTime.now()));
        }
        if (filter == TodoFilter.NO_DATE) {
            return base.and(TODO_TASK.DUE_DATE_TIME.isNull());
        }
        if (filter == TodoFilter.COMPLETED) {
            return base.and(TODO_TASK.COMPLETE_TIME.isNotNull());
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
    public long countUncompletedTasks(long userId, TodoFilter filter) {
        QueryCondition condition = generateCondition(userId, filter).and(TODO_TASK.COMPLETE_TIME.isNull());
        return todoTaskMapper.selectCountByCondition(condition);
    }
}
