package com.inlym.lifehelper.location.position.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据 IP 地址能够获得的位置信息
 *
 * @author inlym
 * @date 2022-01-19
 * @since 1.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IpLocation {
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

    /**
     * 获取默认 IP 定位
     *
     * <h2>主要用途
     * <li>在无法通过 IP 定位又需要一个定位信息时，使用这个默认 IP 定位。
     *
     * @date 2022-10-30
     * @since 1.5.0
     */
    public static IpLocation getDefault() {
        return IpLocation
            .builder()
            .longitude(120.14)
            .latitude(30.23)
            .nation("中国")
            .province("浙江省")
            .city("杭州市")
            .district("西湖区")
            .build();
    }
}
