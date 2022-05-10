package com.inlym.lifehelper.record;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

/**
 * 数据记录服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/5/10
 * @since 1.2.3
 **/
@Service
public class RecordService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RecordService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 存储微信上报信息（目前为临时调试阶段）
     **/
    public void saveMiniProgramLaunchInfo(Object info) {
        redisTemplate
            .opsForValue()
            .set("weixin:launch-info:" + UUID
                .randomUUID()
                .toString()
                .toLowerCase(), info, Duration.ofDays(10));
    }
}
