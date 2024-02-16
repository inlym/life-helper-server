package com.inlym.lifehelper.greatday.entity;

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
 * 纪念日实体
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/6
 * @since 1.8.0
 **/
@Table("great_day")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GreatDay {
    /** 主键 ID */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /** 所属用户 ID */
    private Long userId;

    /** 纪念日名称 */
    private String name;

    /** 日期 */
    private LocalDate date;

    /** emoji 表情 */
    private String icon;

    /** 创建时间（该字段值由数据库自行维护，请勿手动赋值） */
    private LocalDateTime createTime;

    /** 更新时间（该字段值由数据库自行维护，请勿手动赋值） */
    private LocalDateTime updateTime;

    /** 删除时间（逻辑删除标志） */
    @Column(isLogicDelete = true)
    private LocalDateTime deleteTime;
}
