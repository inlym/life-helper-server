package com.inlym.lifehelper.external.lbsqq.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 逆地址解析（将经纬度坐标转换为位置描述）请求响应数据
 * <p>
 * 文档地址：
 * https://lbs.qq.com/service/webService/webServiceGuide/webServiceGcoder
 */
@Data
public class ConvertLocation2AddressResponse {
    /**
     * 状态码，0为正常，其它为异常
     */
    private Integer status;

    /**
     * 对status的描述
     */
    private String message;

    /**
     * 本次请求的唯一标识
     */
    @JsonProperty("request_id")
    private String requestId;

    private Result result;

    @Data
    static class Result {
        /**
         * 以行政区划+道路+门牌号等信息组成的标准格式化地址
         */
        private String address;

        /**
         * 结合知名地点形成的描述性地址，更具人性化特点
         */
        @JsonProperty("formatted_addresses")
        private FormattedAddresses formattedAddresses;

        @Data
        static class FormattedAddresses {
            /**
             * 推荐使用的地址描述，描述精确性较高
             */
            private String recommend;

            /**
             * 粗略位置描述
             */
            private String rough;
        }
    }
}
