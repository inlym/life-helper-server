package com.inlym.lifehelper.hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志调试控制器
 *
 * <h2>主要用途
 * <p>用于调试日志，直接调用接口打印日志。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/2/2
 * @since 1.9.0
 **/
@RestController
@Slf4j
public class LoggerDebugController {
    @GetMapping("/debug/logger/trace")
    public String traceLogger(@RequestParam("msg") String message) {
        log.trace(message);
        return message;
    }

    @GetMapping("/debug/logger/debug")
    public String debugLogger(@RequestParam("msg") String message) {
        log.debug(message);
        return message;
    }
}
