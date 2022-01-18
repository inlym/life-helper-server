package com.inlym.lifehelper.location.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * IP 地址位置信息
 *
 * @author inlym
 * @since 2022-01-18 21:47
 **/
@Data
@NoArgsConstructor
public class IPLocation {
    /** 经度 */
    private Double longitude;

    /** 纬度 */
    private Double latitude;

    /** 国家 */
    private String nation;

    /** 省 */
    private String province;

    /** 市 */
    private String city;

    /** 区 */
    private String district;

    /** 行政区划代码 */
    private String adcode;
}
