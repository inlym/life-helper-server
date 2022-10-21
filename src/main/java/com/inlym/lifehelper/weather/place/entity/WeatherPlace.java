package com.inlym.lifehelper.weather.place.entity;

import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.PrimaryKeyField;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.PrimaryKeyMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 天气地点实体
 *
 * <h2>主要用途
 * <p>为用户关注的城市，选择其中一个查看所在地点天气情况。
 *
 * <h2>数据来源
 * <p>微信小程序中调用 `wx.chooseLocation` 方法获取经纬度信息，再根据经纬度获取省市区信息存入此表。
 *
 * <h2>数据存储
 * <p>当前实体存储在 TableStore 中，未存储在 MySQL 中。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/21
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/location/wx.chooseLocation.html">wx.chooseLocation</a>
 * @since 1.5.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherPlace {
    // ================================= 主键列 =================================

    /** 所属用户 ID - 分区键 */
    @PrimaryKeyField(name = "uid", order = 1, hashed = true)
    private Integer userId;

    /** 地点 ID */
    @PrimaryKeyField(order = 2, mode = PrimaryKeyMode.SIMPLE_UUID)
    private String placeId;

    // ================================= 属性列 =================================

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

    /** 创建时间 */
    private Long createTime;
}
