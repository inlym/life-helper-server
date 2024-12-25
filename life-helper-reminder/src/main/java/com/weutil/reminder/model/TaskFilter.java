package com.weutil.reminder.model;

import lombok.Getter;

/**
 * 待办任务查询过滤器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
@Getter
public enum TaskFilter {
    /** 所有待办（未完成） */
    ALL_UNCOMPLETED,

    /** 已完成 */
    ALL_COMPLETED,

    /** 今天 */
    TODAY,

    /** 已过期 */
    EXPIRED,

    /** 无期限 */
    UNDATED,

    /** 未分类（未选择项目的） */
    UNSPECIFIED
}
