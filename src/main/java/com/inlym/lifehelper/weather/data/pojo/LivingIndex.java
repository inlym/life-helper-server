package com.inlym.lifehelper.weather.data.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 天气生活指数中的单个指数详情
 *
 * @author inlym
 * @date 2022-02-19
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivingIndex {
    // ====================  新增的字段  ====================

    /** 图片 URL */
    private String imageUrl;

    // ====================  原有的字段  ====================

    /** 预报日期 */
    private LocalDate date;

    /** 生活指数类型ID */
    private String type;

    /** 生活指数类型的名称 */
    private String name;

    /** 生活指数预报等级 */
    private String level;

    /** 生活指数预报级别名称 */
    private String category;

    /** 生活指数预报的详细描述，可能为空 */
    private String text;
}
