package com.weutil.todolist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 待办任务视图对象
 *
 * <h2>说明
 * <p>用于客户端展示使用。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/24
 * @since 3.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodolistTaskVO {
    /** 主键 ID */
    private Long id;

    /** 所属项目 ID */
    private Long projectId;

    /** 任务名称 */
    private String name;

    /** 任务描述内容文本 */
    private String content;

    /** 任务完成时间 */
    private LocalDateTime completeTime;

    /** 截止期限的日期部分（年月日） */
    private LocalDate dueDate;

    /** 截止期限的时间部分（时分秒） */
    private LocalTime dueTime;

    /** 优先级 */
    private Priority priority;

    // ============================ 关联字段数据 ============================

    /** 所属的项目名称 */
    private String projectName;
}
