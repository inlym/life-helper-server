package com.weutil.reminder.entity;

import com.mybatisflex.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 任务和标签关联表
 *
 * <h2>说明
 * <p>给1个任务打1个标签为1条记录。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/29
 * @since 3.0.0
 **/
@Table("reminder_link_task_tag")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkTaskTag {
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

    /** 所属任务 ID */
    private Long taskId;

    /** 所属标签 ID */
    private Long tagId;

    // ============================ 关联字段 ============================

    /** 关联的标签 */
    @RelationManyToOne(selfField = "tagId")
    private Tag tag;
}
