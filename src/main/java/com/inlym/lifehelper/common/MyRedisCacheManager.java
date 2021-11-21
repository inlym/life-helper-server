package com.inlym.lifehelper.common;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * 用于设置缓存的有效期（代码是直接从网上复制来的，后期再研读和修改）
 */
public class MyRedisCacheManager extends RedisCacheManager {
    public MyRedisCacheManager(RedisCacheWriter cacheWriter,
                               RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        String[] array = StringUtils.delimitedListToStringArray(name, "#");
        name = array[0];
        if (array.length > 1) { // 解析TTL
            // 例如 12 默认12秒， 12d=12天
            String ttlStr = array[1];
            Duration duration = convertDuration(ttlStr);
            cacheConfig = cacheConfig.entryTtl(duration);
        }
        return super.createRedisCache(name, cacheConfig);
    }

    private Duration convertDuration(String ttlStr) {

        ttlStr = ttlStr.toUpperCase();

        if (ttlStr.lastIndexOf("D") != -1) {
            return Duration.parse("P" + ttlStr);
        }

        return Duration.parse("PT" + ttlStr);
    }
}
