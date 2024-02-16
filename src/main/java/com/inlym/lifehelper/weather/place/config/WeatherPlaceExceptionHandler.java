package com.inlym.lifehelper.weather.place.config;

import com.inlym.lifehelper.common.model.ExceptionResponse;
import com.inlym.lifehelper.weather.place.exception.WeatherPlaceOverflowException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 天气地点模块异常处理器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/21
 * @since 1.5.0
 **/
@RestControllerAdvice
@Order(20)
public class WeatherPlaceExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(WeatherPlaceOverflowException.class)
    public ExceptionResponse handleWeatherPlaceOverflowException(WeatherPlaceOverflowException e) {
        return new ExceptionResponse(1, "您关注的天气地点已超出限制，请删除不需要的地点后再添加！");
    }
}
