package com.inlym.lifehelper.location.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户端 IP 地址信息
 *
 * <h2>说明
 * <p>用于客户端获取 IP 地址及其信息，根据客户端显示需要按需定义字段。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/4/19
 * @since 1.1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IpInfoVO {
    /** IP 地址 */
    private String ip;

    /** 所在地区，格式示例：“浙江省杭州市” */
    private String region;
}
