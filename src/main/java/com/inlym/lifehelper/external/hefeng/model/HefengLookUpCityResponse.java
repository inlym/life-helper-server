package com.inlym.lifehelper.external.hefeng.model;

import lombok.Data;

/**
 * 城市信息查询 HTTP 请求响应数据
 *
 * @author inlym
 * @see <a href="https://dev.qweather.com/docs/api/geo/city-lookup/">城市信息查询</a>
 * @since 2022-01-15 01:35
 **/
@Data
public class HefengLookUpCityResponse {
    /** API状态码 */
    private String code;

    private City[] location;

    @Data
    public static class City {
        /** 地区/城市ID */
        private String id;

        /** 地区/城市名称 */
        private String name;
    }
}
