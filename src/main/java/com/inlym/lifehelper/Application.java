package com.inlym.lifehelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching    // 开启缓存功能
@ServletComponentScan    // 开启过滤器扫描
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
