package com.weutil.reminder.model;

/**
 * 待办任务特殊操作
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
public enum ReminderTaskOperation {
    /** 把待办任务标记为“已完成” */
    COMPLETE,

    /** 把待办任务标记为“未完成” */
    CANCEL_COMPLETE,

    /** 清空待办任务标的截止时间 */
    CLEAR_DUE_TIME,

    /** 置顶 */
    PIN,

    /** 取消置顶 */
    UNPIN
}
