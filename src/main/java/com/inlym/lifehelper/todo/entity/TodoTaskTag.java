package com.inlym.lifehelper.todo.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 任务标签记录
 * <p>
 * <h2>主要用途
 * <p>记录给任务打标签的操作，一个操作一条记录。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/11/21
 * @since 2.0.3
 **/
@Table("todo_task_tag")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoTaskTag {
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

    /** 对应的任务 ID */
    private Long taskId;

    /** 对应的标签 ID */
    private Long tagId;
}
