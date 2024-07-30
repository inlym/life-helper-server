package com.weutil.reminder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 待办任务视图对象
 *
 * <h2>说明
 * <p>用于输出给客户端展示使用。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/30
 * @since 3.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskVO {
    /** 主键 ID */
    private Long id;

    /** 任务名称 */
    private String name;

    /** 任务描述内容文本 */
    private String content;

    /** 任务优先级（枚举值） */
    private TaskPriority priority;

    /** 截止日期 */
    private LocalDate dueDate;

    /** 截止时间 */
    private LocalTime dueTime;
}
