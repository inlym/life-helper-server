package com.weutil.todo.model;

import lombok.Getter;

/**
 * 待办任务过滤器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2025/1/8
 * @since 3.0.0
 **/
@Getter
public enum TodoFilter {
    /** 所有 */
    ALL,

    /** 收集箱（即未设置项目的） */
    INBOX,

    /** 今天 */
    TODAY,

    /** 最近7天 */
    NEXT_SEVEN_DAYS,

    /** 已过期 */
    OVERDUE,

    /** 未设置截止日期的 */
    NO_DATE,

    /** 已完成 */
    COMPLETED
}
