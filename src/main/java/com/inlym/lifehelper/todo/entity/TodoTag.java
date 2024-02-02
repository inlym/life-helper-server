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
 * 标签
 * <p>
 * <h2>实体关系
 * <p>标签（tag）是作用在任务（task）上，两者是多对多关系。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/11/21
 * @since 2.0.3
 **/
@Table("todo_tag")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoTag {
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

    /** 标签名称 */
    private String name;

    /**
     * 置顶时间
     * <p>
     * <h2>字段说明
     * <p>（1）置顶：更新该字段，按照时间降序排列。
     * <p>（2）取消置顶：将该字段置空。
     */
    private LocalDateTime pinTime;
}
