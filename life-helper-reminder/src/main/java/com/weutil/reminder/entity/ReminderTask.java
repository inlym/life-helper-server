package com.weutil.reminder.entity;

import com.mybatisflex.annotation.RelationManyToOne;
import com.mybatisflex.annotation.Table;
import com.weutil.common.entity.BaseUserRelatedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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

    /** 截止时间 */
    private LocalDateTime dueTime;

    // ============================ 关联字段 ============================

    @RelationManyToOne(selfField = "projectId")
    private ReminderProject project;
}
