package com.weutil.system.controller;

import com.weutil.common.annotation.UserPermission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名称
 *
 * <h2>说明
 * <p>说明文本内容
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @since 3.0.0
 **/
@RestController
public class TempController {
    @GetMapping("/temp/a")
    public Map a() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", LocalDateTime.now());
        map.put("b", Calendar.getInstance());

        return map;
    }

    @GetMapping("/temp/b")
    @UserPermission
    public Map b() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", LocalDateTime.now());
        map.put("b", Calendar.getInstance());

        return map;
    }
}
