package com.weutil.system.service;

import com.weutil.common.exception.UnpredictableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 项目启动时间服务
 *
 * <h2>说明
 * <p>用语处理和计算启动时间相关事项
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/4
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class LaunchTimeService {
    /** 存储在 Redis 中的键名 */
    private static final String REDIS_KEY = "system:launch-time";

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 在项目启动时记录
     *
     * @date 2024/12/4
     * @since 3.0.0
     */
    public void recordOnStartUp() {
        LocalDateTime now = LocalDateTime.now();
        log.debug("[启动时任务] 项目启动时间: {}", now);
        stringRedisTemplate.opsForValue().set(REDIS_KEY, now.toString());
    }

    /**
     * 获取项目启动时间
     *
     * @return 项目启动时间
     * @date 2024/12/4
     * @since 3.0.0
     */
    public LocalDateTime getLaunchTime() {
        String str = stringRedisTemplate.opsForValue().get(REDIS_KEY);
        if (str == null) {
            throw new UnpredictableException("未在 Redis 中获取到项目启动时间数据！");
        }

        return LocalDateTime.parse(str);
    }
}
