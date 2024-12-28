package com.weutil.reminder.service;

import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import com.weutil.common.exception.UnpredictableException;
import com.weutil.reminder.entity.ReminderTask;
import com.weutil.reminder.mapper.ReminderTaskMapper;
import com.weutil.reminder.model.ReminderFilterVO;
import com.weutil.reminder.model.ReminderTaskFilter;
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
    public List<ReminderTask> getTaskListByFilter(long userId, ReminderTaskFilter filter) {
        QueryCondition condition = generateCondition(userId, filter);
        return reminderTaskMapper.selectListWithRelationsByQuery(QueryWrapper.create().where(condition));
    }

    /**
     * 根据过滤器名称构建查询条件
     *
     * <h3>说明
     * <p>除“已完成”、“未完成”外，其余均不附加“未完成”条件。
     *
     * @param userId 用户 ID
     * @param filter 过滤器
     *
     * @date 2024/12/28
     * @since 3.0.0
     */
    private QueryCondition generateCondition(long userId, ReminderTaskFilter filter) {
        QueryCondition base = REMINDER_TASK.USER_ID.eq(userId);

        if (filter == ReminderTaskFilter.ALL_UNCOMPLETED) {
            return base.and(REMINDER_TASK.COMPLETE_TIME.isNull());
        }
        if (filter == ReminderTaskFilter.ALL_COMPLETED) {
            return base.and(REMINDER_TASK.COMPLETE_TIME.isNotNull());
        }
        if (filter == ReminderTaskFilter.TODAY) {
            LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
            return base.and(REMINDER_TASK.DUE_TIME.between(start, end));
        }
        if (filter == ReminderTaskFilter.EXPIRED) {
            return base.and(REMINDER_TASK.DUE_TIME.le(LocalDateTime.now()));
        }
        if (filter == ReminderTaskFilter.UNDATED) {
            return base.and(REMINDER_TASK.DUE_TIME.isNull());
        }
        if (filter == ReminderTaskFilter.UNSPECIFIED) {
            return base.and(REMINDER_TASK.PROJECT_ID.eq(0L));
        }

        throw new UnpredictableException("ReminderTaskFilter 出现了未处理的枚举值");
    }

    /**
     * 列出所有过滤器
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/27
     * @since 3.0.0
     */
    public List<ReminderFilterVO> listFilters(long userId) {
        List<ReminderFilterVO> filters = new ArrayList<>();
        filters.add(generateReminderFilterVO(userId, ReminderTaskFilter.ALL_UNCOMPLETED));
        filters.add(generateReminderFilterVO(userId, ReminderTaskFilter.TODAY));
        filters.add(generateReminderFilterVO(userId, ReminderTaskFilter.EXPIRED));
        filters.add(generateReminderFilterVO(userId, ReminderTaskFilter.UNDATED));
        filters.add(generateReminderFilterVO(userId, ReminderTaskFilter.UNSPECIFIED));
        filters.add(generateReminderFilterVO(userId, ReminderTaskFilter.ALL_COMPLETED));

        return filters;
    }

    /**
     * 生成过滤器视图对象
     *
     * @param userId 用户 ID
     * @param filter 过滤器
     *
     * @date 2024/12/28
     * @since 3.0.0
     */
    private ReminderFilterVO generateReminderFilterVO(long userId, ReminderTaskFilter filter) {
        QueryCondition condition1 = generateCondition(userId, filter);
        QueryCondition condition2 = (filter == ReminderTaskFilter.ALL_COMPLETED || filter == ReminderTaskFilter.ALL_UNCOMPLETED) ? condition1 :
            condition1.and(REMINDER_TASK.COMPLETE_TIME.isNull());
        return ReminderFilterVO.builder().name(filter.getName()).type(filter).num(reminderTaskMapper.selectCountByCondition(condition2)).build();
    }
}
