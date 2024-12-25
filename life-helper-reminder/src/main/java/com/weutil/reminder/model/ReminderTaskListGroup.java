package com.weutil.reminder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 待办任务列表组合
 *
 * <h2>说明
 * <p>包含“已完成”和“未完成”等2份列表。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/24
 * @since 3.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReminderTaskListGroup {
    /** 未完成（进行中）的待办任务列表 */
    private List<ReminderTaskVO> uncompleted;

    /** 已完成的待办任务列表 */
    private List<ReminderTaskVO> completed;
}
