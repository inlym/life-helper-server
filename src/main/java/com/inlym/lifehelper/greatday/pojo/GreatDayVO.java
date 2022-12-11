package com.inlym.lifehelper.greatday.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 纪念日视图对象
 *
 * <h2>主要用途
 * <p>封装用于客户端展示的数据。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/7
 * @since 1.x.x
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GreatDayVO {
    /** 纪念日 ID */
    private String id;

    /** 纪念日名称 */
    private String name;

    /** 日期 */
    private LocalDate date;

    /** emoji 表情 */
    private String icon;

    /** 备注 */
    private String comment;

    /**
     * 今天距离纪念日的天数（纪念日减去今天的天数）
     *
     * <h2>含义
     * <li>[ >0 ]：纪念日在未来，还未到。
     * <li>[ =0 ]：纪念日就是今天。
     * <li>[ <0 ]：纪念日已过去。
     */
    private Long days;
}
