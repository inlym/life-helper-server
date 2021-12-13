package com.inlym.lifehelper.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.lang.reflect.Method;
import java.time.Duration;

@Configuration
public class SpringCacheConfig extends CachingConfigurerSupport {
    private final RedisConnectionFactory redisConnectionFactory;

    public SpringCacheConfig(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Override
    public CacheManager cacheManager() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
            .defaultCacheConfig()
            .entryTtl(Duration.ofDays(10))
            .computePrefixWith(name -> name);

        return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory), redisCacheConfiguration);
    }

    @Override
    public KeyGenerator keyGenerator() {
        return new MyKeyGenerator();
    }

    public static class MyKeyGenerator implements KeyGenerator {
        @Override
        public Object generate(Object target, Method method, Object... params) {

            if (params.length > 0) {
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < params.length; i++) {
                    if (i == 0) {
                        sb.append(":");
                    }
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(params[i].toString());
                }

                return sb.toString();
            } else {
                return "";
            }
        }
    }
}
