package com.inlym.lifehelper.weather.weatherplace.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * 天气地点实体
 *
 * <h2>表名
 * <p>`weather_place`
 *
 * <h2>数据来源
 * <p>微信小程序中调用 `wx.chooseLocation` 方法获取经纬度信息，再根据经纬度获取省市区信息存入此表。
 *
 * <h2>主要用途
 * <p>为用户关注的城市，选择其中一个查看所在地点天气情况。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-13
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/location/wx.chooseLocation.html">wx.chooseLocation</a>
 **/
@Data
public class WeatherPlace {
    /** 主键 ID */
    private Integer id;

    /** 删除时间 */
    @JsonIgnore
    private Date deleteTime;

    /** 所属用户 ID */
    @JsonIgnore
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
