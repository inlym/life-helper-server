package com.inlym.lifehelper.location.pojo;

import lombok.Data;

/**
 * 经纬度坐标
 *
 * @author inlym
 * @date 2022-01-22
 **/
@Data
public class LocationCoordinate {
    /** 经度最小值 */
    public static final Double MIN_LONGITUDE = -180.0;

    /** 经度最大值 */
    public static final Double MAX_LONGITUDE = 180.0;

    /** 纬度最小值 */
    public static final Double MIN_LATITUDE = -85.0;

    /** 纬度最大值 */
    public static final Double MAX_LATITUDE = 85.0;

    /** 经度 */
    private Double longitude;

    /** 纬度 */
    private Double latitude;

    public LocationCoordinate(double longitude, double latitude) {
        if (longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE) {
            throw new IllegalArgumentException("经度范围应为 -180~180，当前为 " + longitude);
        }

        if (latitude < MIN_LATITUDE && latitude > MAX_LATITUDE) {
            throw new IllegalArgumentException("纬度范围应为 -85~85，当前为 " + latitude);
        }

        this.longitude = longitude;
        this.latitude = latitude;
    }
}
