package com.inlym.lifehelper.weather.weatherdata.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 天气灾害预警列表项数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/4/30
 * @since 1.2.0
 **/
@Data
public class WeatherWarningItem {
    // =============================  数据处理后新增的字段  =============================

    /** 预警等级 ID */
    private String levelId;

    /** 图片的 URL 地址 */
    private String imageUrl;

    /** 图标的 URL 地址 */
    private String iconUrl;

    // =============================  和风天气原有的字段  ==============================

    /** 本条预警的唯一标识，可判断本条预警是否已经存在 */
    private String id;

    /** 预警发布单位，可能为空 */
    private String sender;

    /** 预警发布时间 */
    private Date pubTime;

    /** 预警信息标题 */
    private String title;

    /** 预警等级，目前包含：白色、蓝色、绿色、黄色、橙色、红色、黑色 */
    private String level;

    /** 预警类型 ID */
    private String type;

    /** 预警类型名称 */
    private String typeName;

    /** 预警详细文字描述 */
    private String text;
}
