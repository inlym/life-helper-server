package com.inlym.lifehelper.location.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * 地址信息
 * <p>
 * [说明]
 * 将腾讯位置服务的逆地址解析数据做了整合
 *
 * @author inlym
 * @date 2022-02-14
 **/
@Data
@Builder
public class AddressComponent {
    /** 以行政区划+道路+门牌号等信息组成的标准格式化地址 */
    private String address;

    /** 推荐使用的地址描述，描述精确性较高 */
    private String recommendAddresses;

    /** 国家 */
    private String nation;

    /** 省 */
    private String province;

    /** 市，如果当前城市为省直辖县级区划，city与district字段均会返回此城市 */
    private String city;

    /** 区，可能为空字串 */
    private String district;

    /** 街道，可能为空字串 */
    private String street;
}
