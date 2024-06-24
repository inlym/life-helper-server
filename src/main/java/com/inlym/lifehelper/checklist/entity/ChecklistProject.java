package com.inlym.lifehelper.checklist.entity;

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
 * 待办清单（即项目）
 *
 * <h2>说明
 * <p>注意项目中提到的「待办清单」就是指「项目（Project）」。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/11
 * @since 2.3.0
 **/
@Table("checklist_project")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistProject {
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

    /** 项目名称 */
    private String name;

    /**
     * 标记颜色
     *
     * <h3>说明
     * <p>用于标记项目名称旁的圆点颜色。
     *
     * <h3>示例值
     * <p>{@code #2ba245}, {@code #282a36}, ...
     */
    private String color;

    /**
     * 置顶时间
     *
     * <h3>说明
     * <p>1. 操作 [置顶] 时，该字段存入当前时间。
     * <p>2. 操作 [取消置顶] 时，将该字段置空。
     */
    private LocalDateTime pinTime;
}
