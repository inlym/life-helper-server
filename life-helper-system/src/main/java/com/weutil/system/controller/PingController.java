package com.weutil.system.controller;

import com.weutil.common.model.ErrorResponse;
import com.weutil.system.model.PingResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 连通性测试控制器
 *
 * <h2>说明
 *
 * <p>说明文本内容
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/30
 * @since 2.3.0
 */
@RestController
@RequiredArgsConstructor
public class PingController {
    private final JdbcTemplate jdbcTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 跟路由调试
     *
     * @date 2024/6/30
     * @since 2.3.0
     */
    @GetMapping("/")
    public ErrorResponse root() {
        return new ErrorResponse(0, "OK");
    }

    /**
     * 接口连通性测试
     *
     * <h2>主要用途
     *
     * <p>用于负载均衡健康检查，只要项目没挂，这个接口就可以正常返回。
     *
     * <h2>注意事项
     *
     * <p>项目内部进行日志记录时，不要记录当前接口，因为负载均衡的健康检查频率为1次/秒，记录日志会造成大量无效日志。
     *
     * @date 2024/6/30
     * @since 2.3.0
     */
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    /**
     * 测试 MySQL 的延迟（单位：毫秒）
     *
     * @date 2024/6/30
     * @since 2.3.0
     */
    @GetMapping("/ping/mysql")
    public PingResultVO pingMysql() {
        long startTime = System.currentTimeMillis();
        jdbcTemplate.execute("select 1");
        long endTime = System.currentTimeMillis();

        return PingResultVO.builder().delay(endTime - startTime).build();
    }

    /**
     * 测试 Redis 的延迟（单位：毫秒）
     *
     * @date 2024/6/30
     * @since 2.3.0
     */
    @GetMapping("/ping/redis")
    public PingResultVO pingRedis() {
        long startTime = System.currentTimeMillis();
        stringRedisTemplate
                .opsForValue()
                .set("temp:last-ping", LocalDateTime.now().toString(), Duration.ofMinutes(1L));
        long endTime = System.currentTimeMillis();

        return PingResultVO.builder().delay(endTime - startTime).build();
    }
}
