package com.inlym.lifehelper.weather.data.pojo;

import lombok.Data;

/**
 * 天气生活指数中的单个指数详情
 *
 * @author inlym
 * @date 2022-02-19
 **/
@Data
public class IndicesItem {
    // ====================  新增的字段  ====================

    /** 图片 URL */
    private String imageUrl;

    // ====================  原有的字段  ====================

    /** 预报日期 */
    private String date;

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
