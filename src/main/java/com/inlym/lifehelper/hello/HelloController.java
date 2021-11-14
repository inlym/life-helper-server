package com.inlym.lifehelper.hello;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
}
