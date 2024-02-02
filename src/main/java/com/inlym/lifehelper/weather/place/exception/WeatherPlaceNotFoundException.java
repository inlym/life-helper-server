package com.inlym.lifehelper.weather.place.exception;

/**
 * 天气地点未找到异常
 *
 * <h2>以下情况抛出当前异常：
 * <li>1. `placeId` 不存在。
 * <li>2. `placeId` 存在，但是和 `userId` 不匹配。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/21
 * @since 1.5.0
 **/
public class WeatherPlaceNotFoundException extends RuntimeException {
    public WeatherPlaceNotFoundException(String message) {
        super(message);
    }

    public static WeatherPlaceNotFoundException create(long userId, String placeId) {
        String message = "Not Found Weather Place For userId=" + userId + ", placeId=" + placeId;
        return new WeatherPlaceNotFoundException(message);
    }
}
