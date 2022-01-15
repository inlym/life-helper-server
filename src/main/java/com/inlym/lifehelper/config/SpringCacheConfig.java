package com.inlym.lifehelper.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * Spring 缓存配置
 * <p>
 * 注意事项：
 * 1. 注意区分缓存和需要使用 Redis 存储的数据两种内容的区别。
 * 2. 缓存的使用场景是：对于相同的入参，某个方法的返回值预估在短期内不会发生变化，或者即便发生变化仍使用旧数据也无所谓的时候，使用缓存避免重复执行，
 * 提高运行效率。
 */
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
            .entryTtl(Duration.ofMinutes(5))
            .computePrefixWith(name -> name)
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory), redisCacheConfiguration);
    }

    /**
     * [为什么要使用自定义键名规则]:
     * -> 可以跨项目共享缓存数据，默认的规则有点怪异，并不通用，将其转化为通用格式。
     * <p>
     * [为什么要跨项目共享缓存数据]:
     * -> 例如获取指定经纬度的实时天气，是向第三方发送 HTTP 请求获取的数据，这个数据符合使用缓存的场景，除了在当前项目使用到外，
     * -> 笔者还在其他语言框架(Nest.js)的项目中使用。
     */
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
