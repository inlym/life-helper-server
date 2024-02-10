package com.inlym.lifehelper.weather.place2.exception;

/**
 * 天气地点超出最大数量限制异常
 *
 * <h2>主要用途
 * <p>在创建新的天气地点时，检测是否超出最大数量，若超出则抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/27
 * @since 1.5.0
 **/
public class WeatherPlaceOverflowException extends RuntimeException {
    public WeatherPlaceOverflowException(String message) {
        super(message);
    }

    public static WeatherPlaceOverflowException create(long userId) {
        String message = "Weather Places Overflow For userId=" + userId;
        return new WeatherPlaceOverflowException(message);
    }
}
