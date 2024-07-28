package com.weutil.reminder.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDateTime;

/**
 * 任务标签
 *
 * <h2>说明
 * <p>“任务”与“标签”的关系为：多对多
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/29
 * @since 3.0.0
 **/
@Table("reminder_tag")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
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

    /** 标签名称 */
    private String name;

    /**
     * 标记颜色（枚举值）
     *
     * <h3>说明
     * <p>用于标记项目名称旁的圆点颜色。
     */
    private Color color;

    /**
     * 收藏时间
     *
     * <h3>说明
     * <p>使用“时间”字段代替布尔值，在收藏列表排序时，以收藏时间作为排序依据。
     */
    private LocalDateTime favoriteTime;

    /**
     * 未完成的任务数
     *
     * <h3>说明
     * <p>该字段值可以直接从“任务表”计算获得，此处可认为是一个“缓存值”。
     */
    private Long uncompletedTaskCount;
}
