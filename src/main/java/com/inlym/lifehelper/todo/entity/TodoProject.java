package com.inlym.lifehelper.todo.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 项目，即清单
 * <p>
 * <h2>实体关系
 * <p>（1）项目和任务是一对多关系，任何一个任务至多从属于一个项目
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/10/9
 * @since 2.0.3
 **/
@Data
@Table("todo_project")
public class TodoProject {
    /** 自增主键 */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 所属用户 ID
     */
    private Integer userId;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 置顶时间
     * <p>
     * <h2>字段说明
     * <p>（1）置顶：更新该字段，按照时间降序排列。
     * <p>（2）取消置顶：将该字段置空。
     */
    private LocalDateTime pinTime;

    /**
     * 逻辑删除时间
     */
    @Column(isLogicDelete = true)
    private LocalDateTime deleteTime;

    /**
     * 乐观锁，即数据修改次数
     */
    @Column(version = true)
    private Integer version;
}
