package com.inlym.lifehelper.greatday.entity;

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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GreatDay {
    // ================================= 主键列 =================================

    /** 所属用户 ID - 分区键 */
    private Integer userId;

    /** 纪念日 ID */
    private Long dayId;

    // ================================= 属性列 =================================

    /** 纪念日名称 */
    private String name;

    /** 日期 */
    private LocalDate date;

    /** emoji 表情 */
    private String icon;
}
