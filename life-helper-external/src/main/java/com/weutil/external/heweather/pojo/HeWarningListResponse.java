package com.weutil.external.heweather.pojo;

import lombok.Data;

/**
 * 获取天气预警城市列表响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/19
 * @see <a href="https://dev.qweather.com/docs/android-sdk/warning/android-weather-warning-city-list/">天气预警城市列表</a>
 * @since 1.5.0
 **/
@Data
public class HeWarningListResponse {
    /** API 状态码 */
    private String code;

    /** 当前 API 的最近更新时间 */
    private String updateTime;

    private WarningLocation[] warningLocList;

    @Data
    public static class WarningLocation {
        /** 当前国家预警的 LocationID */
        private String locationId;
    }
}
