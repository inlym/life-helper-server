package com.inlym.lifehelper.hello;

import com.inlym.lifehelper.common.annotation.ClientIp;
import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.common.model.CustomRequestContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 调试控制器
 *
 * <h2>主要用途
 * <p>用于在项目运行中，手动调用进行特定的线上调试，以验证正确性。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/5
 * @since 1.9.4
 **/
@RestController
@Slf4j
@RequiredArgsConstructor
public class DebugController {
    private final Environment environment;

    /**
     * 查看请求详情
     *
     * @param request 请求
     * @param headers 请求头
     * @param body    请求数据
     *
     * @since 1.9.4
     */
    @RequestMapping("/debug")
    public Map<String, Object> getRequestDetail(HttpServletRequest request, @RequestHeader Map<String, String> headers,
                                                @RequestParam Map<String, String> params, @RequestBody(required = false) String body, @ClientIp String ip,
                                                @RequestParam(value = "sleep", required = false, defaultValue = "0") long timeout) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("path", request.getServletPath());
        map.put("method", request.getMethod());
        map.put("params", params);
        map.put("querystring", request.getQueryString());
        map.put("headers", headers);
        map.put("ip", ip);
        map.put("body", body);

        sleep(timeout);

        return map;
    }

    /**
     * 原样返回请求数据
     *
     * @param body 请求数据
     *
     * @date 2024/3/4
     * @since 2.3.0
     */
    @RequestMapping("/debug/data")
    public String mirrorData(@RequestParam(value = "sleep", required = false, defaultValue = "0") long timeout, @RequestBody(required = false) String body) {
        sleep(timeout);
        return body;
    }

    /**
     * 获取时间相关参数
     *
     * @since 1.9.4
     */
    @GetMapping("/debug/time")
    public Map<String, Object> getTimeArgs() {
        Map<String, Object> map = new HashMap<>(32);
        map.put("System.currentTimeMillis()", System.currentTimeMillis());
        map.put("new Date()", new Date());

        map.put("ZoneId.systemDefault().getId()", ZoneId
            .systemDefault()
            .getId());
        map.put("ZoneId.systemDefault().toString()", ZoneId
            .systemDefault()
            .toString());

        map.put("Calendar.getInstance()", Calendar.getInstance());
        map.put("Calendar.getInstance().getTimeZone()", Calendar
            .getInstance()
            .getTimeZone());

        map.put("LocalDateTime.now()", LocalDateTime.now());
        map.put("LocalDateTime.now().toString()", LocalDateTime
            .now()
            .toString());

        map.put("LocalDate.now()", LocalDate.now());
        map.put("LocalDate.now().toString()", LocalDate
            .now()
            .toString());

        map.put("LocalTime.now()", LocalTime.now());
        map.put("LocalTime.now().toString()", LocalTime
            .now()
            .toString());

        return map;
    }

    //    logging:
    //        # 日志等级
    //    level:
    //    com.inlym.lifehelper: trace

    /**
     * 查看 Spring 配置文件的参数
     *
     * @since 1.9.4
     */
    @GetMapping("/debug/env")
    public Map<String, Object> getProfile() {
        Map<String, Object> map = new HashMap<>(16);
        map.put("spring.profiles.active", environment.getProperty("spring.profiles.active"));
        map.put("server.port", environment.getProperty("server.port"));
        map.put("logging.level.com.inlym.lifehelper", environment.getProperty("logging.level.com.inlym.lifehelper"));

        return map;
    }

    /**
     * 查看用户 ID
     *
     * <h2>说明
     * <p>携带鉴权信息访问时，如果鉴权通过会返回用户 ID，否则报错。
     *
     * @param userId 用户 ID
     *
     * @since 1.9.4
     */
    @GetMapping("/debug/userid")
    @UserPermission
    public Map<String, Object> getUserId(@UserId long userId) {
        return Map.of("userId", userId);
    }

    /**
     * 查看自定义请求上下文
     *
     * @param request 请求
     *
     * @since 1.9.5
     */
    @GetMapping("/debug/ctx")
    public CustomRequestContext getCustomRequestContext(HttpServletRequest request) {
        CustomRequestContext context = (CustomRequestContext) request.getAttribute(CustomRequestContext.NAME);
        log.debug("context={}", context);

        return context;
    }

    /**
     * 查看框架版本号
     *
     * @date 2023/5/22
     * @since 2.0.0
     */
    @GetMapping("/debug/version")
    public Map<String, String> getVersion() {
        Map<String, String> map = new HashMap<>(16);
        map.put("SpringVersion", SpringVersion.getVersion());
        map.put("SpringBootVersion", SpringBootVersion.getVersion());

        return map;
    }

    /**
     * 停顿
     *
     * @param timeout 暂停时长（毫秒）
     *
     * @date 2024/3/4
     * @since 2.3.0
     */
    private void sleep(long timeout) {
        try {
            if (timeout > 0) {
                TimeUnit.MILLISECONDS.sleep(timeout);
            }
        } catch (InterruptedException e) {
            log.debug(e.getMessage());
        }
    }
}
