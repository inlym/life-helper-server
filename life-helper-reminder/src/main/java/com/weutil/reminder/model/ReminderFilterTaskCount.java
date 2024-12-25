package com.weutil.reminder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 各个过滤器任务数组合
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReminderFilterTaskCount {
    /** 所有未完成任务数 */
    private Long allUncompleted;

    /** 今天的未完成任务数 */
    private Long allCompleted;

    /** 所有已完成任务数 */
    private Long today;

    /** 已过期（并且未完成）的任务数 */
    private Long expired;

    /** 无期限（并且未完成）的任务数 */
    private Long undated;

    /** 未分类（并且未完成）的任务数 */
    private Long unspecified;
}
