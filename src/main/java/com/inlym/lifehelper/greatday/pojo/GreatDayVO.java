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
 * @since 2.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GreatDayVO {
    /** 纪念日 ID */
    private Long id;

    /** 纪念日名称 */
    private String name;

    /** 日期 */
    private LocalDate date;

    /** emoji 表情 */
    private String icon;

    /**
     * 今天距离纪念日的天数（纪念日减去今天的天数）
     *
     * <h2>含义
     * <li>[ >0 ]：纪念日在未来，还未到。
     * <li>[ =0 ]：纪念日就是今天。
     * <li>[ <0 ]：纪念日已过去。
     */
    private Long days;

    /**
     * 是否是系统创建的
     *
     * <h2>主要用途
     * <p>用户数据为空时，将返回由系统提供的默认数据，该类数据不允许用户操作（编辑/删除）。因此使用该字段标记该类数据。
     *
     * <h2>字段说明
     * <p>（1）若为系统创建的，则该字段将返回 `true` 值。
     * <p>（2）若为用户本身的数据，则不返回该字段。
     *
     * @date 2023/5/31
     * @since 2.0.1
     */
    private Boolean systemCreated;
}
