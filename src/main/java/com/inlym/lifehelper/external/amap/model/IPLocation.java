package com.inlym.lifehelper.external.amap.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据 IP 地址能够获得的位置信息
 *
 * @author inlym
 * @since 2022-01-19 20:13
 **/
@Data
@NoArgsConstructor
public class IPLocation {
    /** 经度 */
    private Double longitude;

    /** 纬度 */
    private Double latitude;

    /** 国家 */
    private String country;

    /** 省 */
    private String province;

    /** 市 */
    private String city;

    /** 区 */
    private String district;
}
