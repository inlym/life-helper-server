package com.weutil.reminder.entity;

import com.mybatisflex.annotation.*;
import com.weutil.reminder.model.TaskPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 待办任务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/29
 * @since 3.0.0
 **/
@Table("reminder_task")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    // ============================ 通用字段 ============================

    /** 主键 ID */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 删除时间（逻辑删除标志） */
    @Column(isLogicDelete = true)
    private LocalDateTime deleteTime;

    // ============================ 业务字段 ============================

    /** 所属用户 ID */
    private Long userId;

    /**
     * 所属项目 ID
     *
     * <h3>说明
     * <p>该值为 {@code 0} 则表示不归属于任何项目。
     */
    private Long projectId;

    /** 任务名称 */
    private String name;

    /** 任务描述内容文本 */
    private String content;

    /** 任务完成时间 */
    private LocalDateTime completeTime;

    /** 任务优先级（枚举值） */
    private TaskPriority priority;

    /** 截止日期 */
    private LocalDate dueDate;

    /** 截止时间 */
    private LocalTime dueTime;

    // ============================ 关联字段 ============================

    /** 关联的任务标签关联记录 */
    @RelationOneToMany(selfField = "id", targetField = "taskId")
    private List<LinkTaskTag> tags;
}
