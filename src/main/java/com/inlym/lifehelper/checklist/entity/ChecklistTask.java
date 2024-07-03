package com.inlym.lifehelper.checklist.entity;

import com.inlym.lifehelper.checklist.constant.ContentType;
import com.inlym.lifehelper.checklist.constant.TaskPriority;
import com.inlym.lifehelper.checklist.constant.TaskStatus;
import com.inlym.lifehelper.checklist.model.Sortable;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 待办任务
 *
 * <h2>各实体关系
 * <p>1. [项目 vs 任务] = 1 对 多
 * <p>2. [标签 vs 任务] = 多 对 多
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/11
 * @since 2.3.0
 */
@Table("checklist_task")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistTask implements Sortable {
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

    /** 所属项目 ID */
    private Long projectId;

    /** 任务名称 */
    private String name;

    /** 任务描述内容类型（枚举值） */
    private ContentType contentType;

    /** 任务描述内容文本 */
    private String content;

    /** 任务状态（枚举值） */
    private TaskStatus status;

    /** 任务完成时间 */
    private LocalDateTime completeTime;

    /** 任务优先级（枚举值） */
    private TaskPriority priority;

    /** 截止日期 */
    private LocalDate dueDate;

    /** 截止时间 */
    private LocalTime dueTime;

    /**
     * （在排序中的）前一个 ID
     *
     * <h3>说明
     * <p>该字段仅用于组内排序使用，排在第1个（最前面）的该字段赋值为 {@code 0}
     */
    private Long prevId;
}
