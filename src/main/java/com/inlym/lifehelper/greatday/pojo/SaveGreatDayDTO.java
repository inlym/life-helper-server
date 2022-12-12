package com.inlym.lifehelper.greatday.pojo;

import lombok.Data;

import java.time.LocalDate;

/**
 * 新增或更新纪念日数据的请求数据
 *
 * <h2>主要用途
 * <p>封装请求数据，可用于“新增”和“更新”情况。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/7
 * @since 1.8.0
 **/
@Data
public class SaveGreatDayDTO {
    /** 纪念日名称 */
    private String name;

    /** 日期 */
    private LocalDate date;

    /** emoji 表情 */
    private String icon;
}
