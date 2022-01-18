package com.inlym.lifehelper.external.lbsqq.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * IP 定位 HTTP 请求响应数据
 *
 * @author inlym
 * @see <a href="https://lbs.qq.com/service/webService/webServiceGuide/webServiceIp">IP 定位</a>
 * @since 2022-01-17 20:09
 */
@Data
public class LbsqqLocateIPResponse {
    /** 状态码，0为正常，其它为异常 */
    private Integer status;

    /** 对status的描述 */
    private String message;

    /** IP定位结果 */
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

        @Data
        public static class Location {
            /** 经度 */
            @JsonProperty("lng")
            private Double longitude;

            /** 纬度 */
            @JsonProperty("lat")
            private Double latitude;
        }

        @Data
        public static class AddressInfo {
            /** 国家 */
            private String nation;

            /** 省 */
            private String province;

            /** 市 */
            private String city;

            /** 区 */
            private String district;

            /** 行政区划代码 */
            private Integer adcode;
        }
    }
}
