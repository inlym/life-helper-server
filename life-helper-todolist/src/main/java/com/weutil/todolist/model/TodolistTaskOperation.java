package com.weutil.todolist.model;

/**
 * 待办任务特殊操作
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
public enum TodolistTaskOperation {
    /** 把待办任务标记为“已完成” */
    COMPLETE,

    /** 把待办任务标记为“未完成” */
    UNCOMPLETE,

    /** 清空截止期限 */
    CLEAR_DUE_DATETIME,

    /** 置顶 */
    PIN,

    /** 取消置顶 */
    UNPIN
}
