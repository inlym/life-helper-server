package com.inlym.lifehelper.location.region.exception;

/**
 * 无法找到指定地区异常
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/3
 * @since 1.7.2
 **/
public class RegionNotFoundException extends RuntimeException {
    public RegionNotFoundException() {
        super();
    }

    public RegionNotFoundException(String message) {
        super(message);
    }
}
