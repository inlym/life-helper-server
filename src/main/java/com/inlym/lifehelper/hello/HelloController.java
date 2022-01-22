package com.inlym.lifehelper.hello;

import com.inlym.lifehelper.common.annotation.UserPermission;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口调试控制器
 *
 * @author inlym
 * @since 2022-01-22 20:21
 */
@RestController
public class HelloController {
    @GetMapping("/")
    public String index() {
        return "hello inlym";
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    /**
     * 用于反射请求
     */
    @RequestMapping("/debug")
    public Map<String, Object> debug(@RequestParam Map<String, String> params, @RequestHeader Map<String, String> headers, @Nullable @RequestBody Object body) {
        Map<String, Object> map = new HashMap<>();

        map.put("params", params);
        map.put("headers", headers);

        if (body != null) {
            map.put("body", body);
        }

        return map;
    }

    @GetMapping("/debug/auth")
    public Object auth() {
        return SecurityContextHolder
            .getContext()
            .getAuthentication();
    }

    @GetMapping("/debug/auth/user")
    @UserPermission
    public Object userRole() {
        return SecurityContextHolder
            .getContext()
            .getAuthentication();
    }
}
