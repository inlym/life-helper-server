package com.inlym.lifehelper.external.hefeng.model;

import lombok.Data;

@Data
public class HefengLookUpCityResponse {
    /** API状态码 */
    private String code;

    private City[] location;

    @Data
    public static class City {
        /** 地区/城市ID */
        private String id;

        /** 地区/城市名称 */
        private String name;
    }
}
