package com.inlym.lifehelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableCaching    // 开启缓存的支持
@EnableScheduling    // 开启计划任务的支持
@ServletComponentScan    // 开启过滤器和监听器扫描
@EnableOpenApi    // 开启 Swagger3
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
