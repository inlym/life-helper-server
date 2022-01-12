package com.inlym.lifehelper.external.lbsqq.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * IP 定位请求响应数据
 * <p>
 * 文档地址：
 * https://lbs.qq.com/service/webService/webServiceGuide/webServiceIp
 */
@Data
public class LocateIPResponse {
    /**
     * 状态码，0为正常，其它为异常
     */
    private Integer status;

    /**
     * 对status的描述
     */
    private String message;

    /**
     * IP定位结果
     */
    private Result result;

    @Data
    static class Result {
        /**
         * 用于定位的IP地址
         */
        private String ip;

        /**
         * 定位坐标
         */
        private Location location;

        /**
         * 定位行政区划信息
         */
        @JsonProperty("ad_info")
        private AddressInfo addressInfo;

        @Data
        static class Location {
            /**
             * 经度
             */
            @JsonProperty("lng")
            private Double longitude;

            /**
             * 纬度
             */
            @JsonProperty("lat")
            private Double latitude;
        }

        @Data
        static class AddressInfo {
            /**
             * 国家
             */
            private String nation;

            /**
             * 省
             */
            private String province;

            /**
             * 市
             */
            private String city;

            /**
             * 区
             */
            private String district;

            /**
             * 行政区划代码
             */
            private Integer adcode;
        }
    }
}
