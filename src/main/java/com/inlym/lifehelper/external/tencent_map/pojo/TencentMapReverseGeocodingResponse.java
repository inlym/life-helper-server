package com.inlym.lifehelper.external.tencent_map.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 腾讯位置服务逆地址解析响应数据
 *
 * @author inlym
 * @date 2022-02-14 00:52
 * @see <a href="https://lbs.qq.com/service/webService/webServiceGuide/webServiceGcoder">逆地址解析</a>
 **/
@Data
public class TencentMapReverseGeocodingResponse {
    /** 状态码，0为正常，其它为异常 */
    private Integer status;

    /** 对 status 的描述 */
    private String message;

    /** 本次请求的唯一标识 */
    @JsonProperty("request_id")
    private String requestId;

    private ReverseGeocodingResult result;

    @Data
    public static class ReverseGeocodingResult {
        /** 以行政区划+道路+门牌号等信息组成的标准格式化地址 */
        private String address;

        @JsonProperty("formatted_addresses")
        private FormattedAddresses formattedAddresses;

        @JsonProperty("address_component")
        private AddressComponent addressComponent;
    }

    /** 结合知名地点形成的描述性地址，更具人性化特点 */
    @Data
    public static class FormattedAddresses {
        /** 推荐使用的地址描述，描述精确性较高 */
        private String recommend;

        /** 粗略位置描述 */
        private String rough;
    }

    /** 地址部件 */
    @Data
    public static class AddressComponent {
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

        /** 门牌，可能为空字串 */
        @JsonProperty("street_number")
        private String streetNumber;
    }
}
