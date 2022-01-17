package com.inlym.lifehelper.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 *
 * @since 2022-01-17 22:31
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Log logger = LogFactory.getLog(getClass());

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handler(Exception e) {
        logger.error(e.getMessage());
        Map<String, Object> map = new HashMap<>();

        map.put("errCode", 50000);
        map.put("errMsg", "内部错误");

        return map;
    }
}
