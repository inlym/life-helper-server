package com.weutil.system.startup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 项目启动时间记录任务
 *
 * <h2>说明
 * <p>记录项目的本期启动时间，以便排查项目是否正常启动。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/3
 * @since 3.0.0
 **/
@Component
@Slf4j
@RequiredArgsConstructor
public class LaunchTimeSavingRunner implements CommandLineRunner {
    public static final String REDIS_KEY = "system:launch-time";

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(String... args) {
        LocalDateTime now = LocalDateTime.now();
        log.debug("[启动时任务] 项目启动时间: {}", now);
        stringRedisTemplate.opsForValue().set(REDIS_KEY, now.toString());
    }
}
