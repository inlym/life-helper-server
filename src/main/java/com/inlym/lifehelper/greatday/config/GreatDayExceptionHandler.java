package com.inlym.lifehelper.greatday.config;

import com.inlym.lifehelper.common.model.ErrorResponse;
import com.inlym.lifehelper.greatday.exception.GreatDayNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 纪念日模块异常处理器
 *
 * <h2>主要用途
 * <p>捕获「纪念日模块」模块内抛出的异常并处理。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/9
 * @since 1.8.0
 **/
@RestController
@Slf4j
@Order(30)
public class GreatDayExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(GreatDayNotFoundException.class)
    public ErrorResponse handleGreatDayNotFoundException() {
        return new ErrorResponse(12001, "你操作的纪念日不存在！");
    }
}
