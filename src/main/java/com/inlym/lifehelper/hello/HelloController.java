package com.inlym.lifehelper.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
