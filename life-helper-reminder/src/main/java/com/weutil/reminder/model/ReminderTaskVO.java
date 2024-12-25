package com.weutil.reminder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
public class ReminderTaskVO {
    /** 主键 ID */
    private Long id;

    /** 所属项目 ID */
    private Long projectId;

    /** 任务名称 */
    private String name;

    /** 任务描述内容文本 */
    private String content;

    /** 截止时间 */
    private LocalDateTime dueTime;

    // ============================ 关联字段数据 ============================

    /** 所属的项目名称 */
    private String projectName;
}
