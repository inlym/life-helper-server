package com.inlym.lifehelper.todo.entity;

import com.inlym.lifehelper.todo.constant.TaskPriority;
import com.inlym.lifehelper.todo.constant.TaskStatus;
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

/**
 * 任务
 * <p>
 * <h2>实体关系
 * <p>项目和任务是一对多关系。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/11/21
 * @since 2.0.3
 **/
@Table("todo_task")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoTask {
    // ================================= 公共字段 =================================

    /** 自增主键 */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /** 创建时间 */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /** 更新时间 */
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;

    /** 删除时间，用作逻辑删除标志 */
    @Column(isLogicDelete = true)
    private LocalDateTime deleteTime;

    /** 版本号，用作乐观锁 */
    @Column(version = true)
    private Integer version;

    // ================================= 业务字段 =================================

    /** 所属用户 ID */
    private Long userId;

    /** 所属项目 ID */
    private Long projectId;

    /** 任务标题 */
    private String title;

    /** 任务描述 */
    private String description;

    /** 任务优先级 */
    private TaskPriority priority;

    /** 任务状态 */
    private TaskStatus status;

    /** 截止日期 */
    private LocalDate dueDate;
}
