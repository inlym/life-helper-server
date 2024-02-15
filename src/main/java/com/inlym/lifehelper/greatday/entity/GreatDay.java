package com.inlym.lifehelper.greatday.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
}
