package com.weutil.todolist.model;

import lombok.Getter;

/**
 * 待办任务查询过滤器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
@Getter
public enum TodolistTaskFilter {
    /** 所有待办（未完成） */
    ALL_UNCOMPLETED("所有待办"),

    /** 已完成 */
    ALL_COMPLETED("已完成"),

    /** 今天 */
    TODAY("今天"),

    /** 已过期 */
    EXPIRED("已过期"),

    /** 无期限 */
    UNDATED("无期限"),

    /** 未分类（未选择项目的） */
    UNSPECIFIED("未分类");

    /** 过滤器名称 */
    private final String name;

    TodolistTaskFilter(String name) {
        this.name = name;
    }
}
