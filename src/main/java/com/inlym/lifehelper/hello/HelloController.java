package com.inlym.lifehelper.hello;

import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.common.constant.CustomRequestAttribute;
import com.inlym.lifehelper.common.constant.Role;
import org.springframework.lang.Nullable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public Object debug(@RequestParam Map<String, String> params, @RequestHeader Map<String, String> headers, @Nullable @RequestBody Object body) {
        Map<String, Object> map = new HashMap<>(16);

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

    @GetMapping("/debug/attr")
    @Secured(Role.DEVELOPER)
    public Object attr(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>(16);
        map.put(CustomRequestAttribute.REQUEST_ID, request.getAttribute(CustomRequestAttribute.REQUEST_ID));
        map.put(CustomRequestAttribute.USER_ID, request.getAttribute(CustomRequestAttribute.USER_ID));
        map.put(CustomRequestAttribute.CLIENT_IP, request.getAttribute(CustomRequestAttribute.CLIENT_IP));

        return map;
    }
}
