package com.inlym.lifehelper.external.heweather.pojo;

import lombok.Data;

/**
 * 城市信息查询响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/3/26
 * @see <a href="https://dev.qweather.com/docs/api/geoapi/city-lookup/">城市信息查询</a>
 * @since 1.4.0
 **/
@Data
public class HeCityLookupResponse {
    /** API 状态码 */
    private String code;

    /** 城市列表 */
    private City[] location;

    @Data
    public static class City {
        /** 地区/城市名称 */
        private String name;

        /** 地区/城市 ID */
        private String id;

        /** 地区/城市纬度 */
        private String lat;

        /** 地区/城市经度 */
        private String lon;

        /** 地区/城市的上级行政区划名称 */
        private String adm2;

        /** 地区/城市所属一级行政区域 */
        private String adm1;

        /** 地区/城市所属国家名称 */
        private String country;
    }
}
