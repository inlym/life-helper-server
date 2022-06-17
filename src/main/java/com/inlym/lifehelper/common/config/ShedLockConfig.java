package com.inlym.lifehelper.common.config;

import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * ShedLock 配置
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/17
 * @since 1.3.0
 **/
@Configuration
@EnableSchedulerLock(defaultLockAtLeastFor = "PT5S", defaultLockAtMostFor = "PT30S")
@RequiredArgsConstructor
public class ShedLockConfig {
    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    public LockProvider lockProvider() {
        return new RedisLockProvider(redisConnectionFactory);
    }
}
