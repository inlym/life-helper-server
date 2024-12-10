package com.weutil.system.service;

import com.weutil.common.util.RandomStringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 延迟时间计算服务
 *
 * <h2>说明
 * <p>计算各个中间件的延迟时间。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/10
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class DelayTimeService {
    private final JdbcTemplate jdbcTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 计算 MySQL 的延迟时间（单位：毫秒）
     *
     * @date 2024/12/10
     * @since 3.0.0
     */
    public long calcMysqlDelayTime() {
        long startTime = System.currentTimeMillis();
        jdbcTemplate.execute("select 1");
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    /**
     * 计算 Redis 的延迟时间（单位：毫秒）
     *
     * @date 2024/12/10
     * @since 3.0.0
     */
    public long calcRedisDelayTime() {
        long startTime = System.currentTimeMillis();
        stringRedisTemplate.opsForValue().set("temp:" + RandomStringUtil.generate(12), LocalDateTime.now().toString(), Duration.ofMinutes(1L));
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }
}
