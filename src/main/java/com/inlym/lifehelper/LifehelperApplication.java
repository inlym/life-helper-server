package com.inlym.lifehelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LifehelperApplication {
    public static void main(String[] args) {
        SpringApplication.run(LifehelperApplication.class, args);
    }
}
