package com.inlym.lifehelper.location.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据 IP 地址能够获得的位置信息
 *
 * @author inlym
 * @date 2022-01-19
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationInfo {
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
}
