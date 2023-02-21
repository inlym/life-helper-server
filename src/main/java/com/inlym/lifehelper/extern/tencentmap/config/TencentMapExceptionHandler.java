package com.inlym.lifehelper.extern.tencentmap.config;

import com.inlym.lifehelper.common.model.ErrorResponse;
import com.inlym.lifehelper.extern.tencentmap.exception.InvalidIpException;
import com.inlym.lifehelper.extern.tencentmap.exception.TencentMapHttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 腾讯位置服务异常处理器
 *
 * <h2>主要用途
 * <p>捕获并处理在「腾讯位置服务」模块中出现的异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/2/21
 * @since 1.9.2
 **/
@RestController
@Slf4j
@Order(20)
public class TencentMapExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InvalidIpException.class)
    public ErrorResponse handleInvalidIpException(InvalidIpException e) {
        log.error(e.getMessage());
        return new ErrorResponse(5000);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TencentMapHttpException.class)
    public ErrorResponse handleTencentMapHttpException(TencentMapHttpException e) {
        log.error(e.getMessage());
        return new ErrorResponse(5000);
    }
}
