package com.inlym.lifehelper.external.amap.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 高德逆地理编码 HTTP 请求响应数据
 *
 * @author inlym
 * @date 2022-02-13 21:34
 * @see <a href="https://lbs.amap.com/api/webservice/guide/api/georegeo#regeo">逆地理编码</a>
 **/
@Data
public class AmapReverseGeocodingResponse {
    /** 状态码：返回结果状态值，值为0或1，0表示失败，1表示成功 */
    private String status;

    /** 返回状态说明：status 为0时，info 返回错误原因；否则返回 “OK” */
    private String info;

    /** 状态码：返回状态说明,10000代表正确 */
    private String infocode;

    /** 逆地理编码数据 */
    private ReverseGeocoding regeocode;

    @Data
    public static class ReverseGeocoding {
        /** 结构化地址信息 */
        @JsonProperty("formatted_address")
        private String formattedAddress;

        /** 地址元素列表 */
        private AddressComponent addressComponent;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddressComponent {
        /** 坐标点所在省 */
        private String province;

        /** 坐标点所在市 */
        private String city;

        /** 城市编码 */
        @JsonProperty("citycode")
        private String cityCode;

        /** 坐标点所在区 */
        private String district;

        /** 行政区编码 */
        private String adcode;

        /** 坐标点所在乡镇/街道 */
        private String township;

        /** 乡镇街道编码 */
        @JsonProperty("towncode")
        private String townCode;
    }
}
