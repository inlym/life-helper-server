package com.inlym.lifehelper.extern.heweather.pojo;

import lombok.Data;

/**
 * 获取太阳高度角响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/5/5
 * @see <a href="https://dev.qweather.com/docs/api/astronomy/solar-elevation-angle/">太阳高度角</a>
 * @since 1.2.1
 **/
@Data
public class HeSolarElevationAngleResponse {
    /** API 状态码 */
    private String code;

    /** 太阳高度角 */
    private String solarElevationAngle;

    /** 太阳方位角，正北顺时针方向角度 */
    private String solarAzimuthAngle;

    /** 太阳时，HHmm格式 */
    private String solarHour;

    /** 时角 */
    private String hourAngle;
}
