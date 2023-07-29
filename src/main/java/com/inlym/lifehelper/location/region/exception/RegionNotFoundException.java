package com.inlym.lifehelper.location.region.exception;

/**
 * 未找到地区异常
 * <p>
 * <h2>主要用途
 * <p>在通过 adcode （即数据表主键 ID）查找时，如果未找到，则抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/7/30
 * @since 2.0.2
 **/
public class RegionNotFoundException extends RuntimeException {
    public RegionNotFoundException(String message) {
        super(message);
    }
}
