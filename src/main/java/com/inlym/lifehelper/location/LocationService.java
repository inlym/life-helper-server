package com.inlym.lifehelper.location;

import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.location.model.IPLocation;

/**
 * 位置服务接口
 * <p>
 * [主要用途]
 * 目前位置服务引用了两个外部服务：高德地图、腾讯地图，为了统一接口，两者的封装类均需实现当前接口，目前首选高德。
 *
 * @author inlym
 * @since 2022-01-19 20:42
 **/
public interface LocationService {
    /**
     * IP 定位
     *
     * @param ip IP 地址
     *
     * @return 经纬度和省市区等信息
     *
     * @throws ExternalHttpRequestException 对外 HTTP 请求异常
     */
    IPLocation locateIp(String ip) throws ExternalHttpRequestException;
}
