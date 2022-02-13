package com.inlym.lifehelper.weather.weather_place.entity;

import lombok.Data;

/**
 * 天气地点实体
 * <p>
 * [表名]: `weather_place`
 * <p>
 * [数据来源]
 * 微信小程序中调用 `wx.chooseLocation` 方法获取经纬度信息，再根据经纬度获取省市区信息存入此表。
 * <p>
 * [主要用途]
 * 为用户关注的城市，选择其中一个查看所在地点天气情况。
 *
 * @author inlym
 * @date 2022-02-13 18:42
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/location/wx.chooseLocation.html">wx.chooseLocation</a>
 **/
@Data
public class WeatherPlace {
    /** 主键 ID */
    private Integer id;

    /** 所属用户 ID */
    private Integer userId;

    /** 位置名称 */
    private String name;

    /** 详细地址 */
    private String address;

    /** 经度 */
    private Double longitude;

    /** 纬度 */
    private Double latitude;

    /** 坐标点所在省 */
    private String province;

    /** 坐标点所在市 */
    private String city;

    /** 坐标点所在区 */
    private String district;
}
