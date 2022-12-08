package com.inlym.lifehelper.greatday.pojo;

import com.inlym.lifehelper.common.validation.SimpleUUID;
import lombok.Data;

import java.time.LocalDate;

/**
 * 编辑纪念日数据的请求数据
 *
 * <h2>主要用途
 * <p>封装请求数据。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/7
 * @since 1.8.0
 **/
@Data
public class UpdateGreatDayDTO {
    /** 纪念日 ID */
    @SimpleUUID
    private String id;

    /** 纪念日名称 */
    private String name;

    /** 日期 */
    private LocalDate date;

    /** emoji 表情 */
    private String icon;

    /** 备注 */
    private String comment;
}
