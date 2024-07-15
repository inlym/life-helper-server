package com.weutil.system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 临时测试控制器
 *
 * <h2>说明
 * <p>仅用于临时测试，非业务功能。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/15
 * @since 3.0.0
 **/
@RestController
@RequiredArgsConstructor
public class TempController {

    @GetMapping("/temp/a")
    public Object a() {
        return null;
    }
}
