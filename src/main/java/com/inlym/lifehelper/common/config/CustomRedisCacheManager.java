package com.inlym.lifehelper.common.config;

import com.inlym.lifehelper.common.constant.RedisCacheCollector;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

/**
 * 自定义 Redis 缓存管理器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/5/8
 * @since 1.2.2
 **/
public class CustomRedisCacheManager extends RedisCacheManager {
    public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        if (RedisCacheCollector.CACHE_DURATION_MAP.get(name) != null) {
            cacheConfig = cacheConfig.entryTtl(RedisCacheCollector.CACHE_DURATION_MAP.get(name));
        }

        return super.createRedisCache(name, cacheConfig);
    }
}
