package com.inlym.lifehelper.weather.weatherplace.pojo;

import lombok.Data;

/**
 * 和风天气中的城市
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/3/26
 **/
@Data
public class HeCity {
    /** 地区/城市 ID */
    private String id;

    /** 地区/城市名称 */
    private String name;

    /** 地区/城市经度 */
    private Double longitude;

    /** 地区/城市纬度 */
    private Double latitude;

    /** 地区/城市的上级行政区划名称 */
    private String adm2;

    /** 地区/城市所属一级行政区域 */
    private String adm1;

    /** 地区/城市所属国家名称 */
    private String country;
}
