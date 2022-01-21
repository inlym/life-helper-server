package com.inlym.lifehelper.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 位置相关工具类
 *
 * @author inlym
 * @since 2022-01-21 23:42
 **/
public final class LocationUtils {
    /**
     * 拆分经纬度字符串
     * <p>
     * [说明]
     * 将 `120.12,30.34` 格式的经纬度字符串生成经纬度坐标实例
     *
     * @param location 经纬度字符串
     */
    public static Coordinate splitLocationString(String location) {
        String[] list = location.split(",");
        if (list.length == Coordinate.ARG_NUM) {
            return new Coordinate(Double.valueOf(list[0]), Double.valueOf(list[1]));
        }

        throw new IllegalArgumentException("不合规的经纬度字符串");
    }

    /**
     * 经纬度坐标
     */
    @Data
    @AllArgsConstructor
    public static class Coordinate {
        /** 参数数量：2个 */
        public static final int ARG_NUM = 2;

        /** 经度 */
        private Double longitude;

        /** 纬度 */
        private Double latitude;
    }
}
