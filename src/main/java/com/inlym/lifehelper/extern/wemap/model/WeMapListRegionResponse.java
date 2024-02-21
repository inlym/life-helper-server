package com.inlym.lifehelper.extern.wemap.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 获取省市区列表请求的响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/1
 * @see <a href="https://lbs.qq.com/service/webService/webServiceGuide/webServiceDistrict">获取省市区列表</a>
 * @since 1.7.0
 **/
@Data
public class WeMapListRegionResponse {
    /** 状态码，0为正常，其它为异常 */
    private Integer status;

    /** 对 status 的描述 */
    private String message;

    /** 数据版本，用于判断更新 */
    @JsonProperty("data_version")
    private String dataVersion;

    private List<List<Region>> result;

    @Data
    public static class Region {
        /** 行政区划唯一标识（adcode） */
        private String id;

        /** 简称 */
        private String name;

        /** 全称 */
        @JsonProperty("fullname")
        private String fullName;

        /** 子级行政区划在下级数组中的下标位置 */
        @JsonProperty("cidx")
        private List<Integer> childrenIndex;
    }
}
