package com.weutil.weather.place.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 天气地点
 *
 * <h2>主要用途
 * <p>为用户关注的城市，选择其中一个查看所在地点天气情况。
 *
 * <h2>数据来源
 * <p>微信小程序中调用 `wx.chooseLocation` 方法获取经纬度信息，再根据经纬度获取省市区信息存入此表。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/16
 * @since 2.1.0
 **/
@Table("weather_place")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherPlace {
    /** 主键 ID */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /** 所属用户 ID */
    private Long userId;

    // 字段说明：以下4个字段直接由客户端提供

    /** 位置名称 */
    private String name;

    /** 详细地址 */
    private String address;

    /** 经度 */
    private Double longitude;

    /** 纬度 */
    private Double latitude;

    // 字段说明：以下3个字段通过上面的经纬度字段通过位置服务查询获得

    /** 坐标点所在省 */
    private String province;

    /** 坐标点所在市 */
    private String city;

    /** 坐标点所在区 */
    private String district;

    // 其他字段

    /** 在和风天气中使用的 LocationID */
    private String locationId;

    /** 创建时间（该字段值由数据库自行维护，请勿手动赋值） */
    private LocalDateTime createTime;

    /** 更新时间（该字段值由数据库自行维护，请勿手动赋值） */
    private LocalDateTime updateTime;

    /** 删除时间（逻辑删除标志） */
    @Column(isLogicDelete = true)
    private LocalDateTime deleteTime;
}
