package com.inlym.lifehelper.location.ip.service;

import com.inlym.lifehelper.extern.tencentmap.TencentMapService;
import com.inlym.lifehelper.extern.tencentmap.pojo.TencentMapLocateIpResponse;
import com.inlym.lifehelper.location.region.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * IP 地址定位服务
 * <p>
 * <h2>主要用途
 * <p>通过 IP 地址获取地区信息。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/8/3
 * @since 2.0.2
 **/
@Service
@RequiredArgsConstructor
public class IpLocationService {
    private final TencentMapService tencentMapService;

    private final RegionService regionService;

    /**
     * 获取一级地区简称
     * <p>
     * <h2>逻辑说明
     * <p>（1）如果是国外地区，则返回国家名称的简称，例如："法国", "美国", "澳大利亚"。
     * <p>（2）如果是国内地区，则返回省级行政单位的简称，例如："浙江", "上海", "香港"。
     *
     * @param ip IP 地址
     */
    public String getPrimaryShortName(String ip) {
        TencentMapLocateIpResponse response = tencentMapService.locateIp(ip);

        // 响应异常，没必要报错，直接返回结果 "未知"
        if (response.getStatus() != 0) {
            return "未知";
        }

        // 响应数据正常，则 `adcode` 一定有值，若为 `-1` 则表示国外，直接取 `nation` 的值
        TencentMapLocateIpResponse.AddressInfo addressInfo = response
            .getResult()
            .getAddressInfo();
        if (addressInfo.getAdcode() == -1) {
            return addressInfo.getNation();
        }

        // `adcode` 值正常情况，响应值的其他几个省市区值为全称，不能直接用，因此需要通过查表获得省级简称
        return regionService.getProvinceShortName(addressInfo.getAdcode());
    }
}
