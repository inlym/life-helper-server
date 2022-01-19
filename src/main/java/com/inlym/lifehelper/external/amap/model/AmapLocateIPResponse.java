package com.inlym.lifehelper.external.amap.model;

import lombok.Data;

/**
 * IP 定位 HTTP 请求响应数据
 *
 * @author inlym
 * @see <a href="https://lbs.amap.com/api/webservice/guide/api/ipconfig">IP 定位</a>
 * @since 2022-01-19 19:52
 **/
@Data
public class AmapLocateIPResponse {
    /** 状态码：返回结果状态值，值为0或1，0表示失败，1表示成功 */
    private String status;

    /** 返回状态说明：status 为0时，info 返回错误原因；否则返回 “OK” */
    private String info;

    /** 状态码：返回状态说明,10000代表正确 */
    private String infocode;

    /** 国家（或地区） */
    private String country;

    /** 省份 */
    private String province;

    /** 城市 */
    private String city;

    /** 区县 */
    private String district;

    /** 运营商：如电信、联通、移动 */
    private String isp;

    /** 经纬度：如 116.480881,39.989410 */
    private String location;

    /** IP地址：提交的 Ipv4/ Ipv6地址 */
    private String ip;
}
