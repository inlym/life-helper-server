package com.weutil.externalal.wemap.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 腾讯位置服务 IP 定位响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/21
 * @see <a href="https://lbs.qq.com/service/webService/webServiceGuide/position/webServiceIp">IP定位</a>
 * @since 2.2.0
 **/
@Data
public class WeMapLocateIpResponse {
    /** 状态码，0为正常，其它为异常 */
    private Integer status;

    /** 对 status 的描述 */
    private String message;

    /** IP 定位结果 */
    private Result result;

    @Data
    public static class Result {
        /** 用于定位的IP地址 */
        private String ip;

        /** 定位坐标 */
        private Location location;

        /** 定位行政区划信息 */
        @JsonProperty("ad_info")
        private AddressInfo addressInfo;
    }

    /** 经纬度坐标 */
    @Data
    public static class Location {
        /** 经度 */
        @JsonProperty("lng")
        private Double longitude;

        /** 纬度 */
        @JsonProperty("lat")
        private Double latitude;
    }

    /** 定位行政区划信息 */
    @Data
    public static class AddressInfo {
        /** 国家 */
        private String nation;

        /** 国家代码（ISO3166标准3位数字码） */
        @JsonProperty("nation_code")
        private String nationCode;

        /** 省 */
        private String province;

        /** 市（可能为空） */
        private String city;

        /** 区（可能为空） */
        private String district;

        /** 行政区划代码（非正常情况则返回 -1） */
        private Integer adcode;
    }
}
