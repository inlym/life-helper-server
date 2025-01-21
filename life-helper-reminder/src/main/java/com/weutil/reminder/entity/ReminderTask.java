package com.weutil.reminder.entity;

import com.mybatisflex.annotation.RelationManyToOne;
import com.mybatisflex.annotation.Table;
import com.weutil.common.entity.BaseUserRelatedEntity;
import com.weutil.reminder.model.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 待办任务实体
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/12
 * @since 3.0.0
 **/
@Table("reminder_task")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ReminderTask extends BaseUserRelatedEntity {

    /** 所属项目 ID */
    private Long projectId;

    /** 任务名称 */
    private String name;

    /** 任务描述内容文本 */
    private String content;

    /** 任务完成时间 */
    private LocalDateTime completeTime;

    /**
     * 截止期限（日期+时间）
     *
     * <h3>字段说明
     * <p>为方便内部计算处理，增加当前冗余字段，处理策略如下：
     * <p>（1）若 {@code dueDate} 为空，则 {@code dueDateTime} 也为空。
     * <p>（2）若 {@code dueDate} 不为空，但 {@code dueTime} 为空，则 {@code dueDateTime} 日期部分保持一致，时间部分替换为最大时间值 {@code 23:59:59}。
     * <p>（3）若 {@code dueDate} 和 {@code dueTime} 均不为空，则 {@code dueDateTime} 填充对应值。
     */
    private LocalDateTime dueDateTime;

    /** 截止期限的日期部分（年月日） */
    private LocalDate dueDate;

    /** 截止期限的时间部分（时分秒） */
    private LocalTime dueTime;

    /** 优先级 */
    private Priority priority;

    // ============================ 关联字段 ============================

    @RelationManyToOne(selfField = "projectId")
    private ReminderProject project;
}
