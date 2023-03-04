package com.inlym.lifehelper.hello;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        map.put("LocalDate.now()", LocalDate.now());
        map.put("LocalTime.now()", LocalTime.now());

        return map;
    }

    /**
     * 查看 Spring 配置文件的参数
     *
     * @since 1.9.4
     */
    @GetMapping("/debug/env")
    public Map<String, Object> getProfile() {
        Map<String, Object> map = new HashMap<>(16);
        map.put("spring.profiles.active", environment.getProperty("spring.profiles.active"));
        map.put("lifehelper.version", environment.getProperty("lifehelper.version"));
        map.put("server.port", environment.getProperty("server.port"));

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
    public Map<String, Object> getUserId(@UserId int userId) {
        return Map.of("userId", userId);
    }

    /**
     * 查看请求详情
     *
     * @param request 请求
     * @param headers 请求头
     * @param body    请求数据
     *
     * @since 1.9.4
     */
    @RequestMapping("/debug/request")
    public Map<String, Object> getRequestDetail(HttpServletRequest request, @RequestHeader Map<String, String> headers, @RequestBody(required = false) String body) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("getRequestURI", request.getRequestURI());
        map.put("getServletPath", request.getServletPath());
        map.put("getMethod", request.getMethod());
        map.put("getParameterMap", request.getParameterMap());
        map.put("getQueryString", request.getQueryString());
        map.put("getAuthType", request.getAuthType());

        map.put("headers", headers);
        map.put("body", body);

        return map;
    }
}
