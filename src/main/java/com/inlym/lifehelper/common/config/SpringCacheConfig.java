package com.inlym.lifehelper.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * Spring 缓存配置
 * <p>
 * <h2>注意事项
 * <li>注意区分缓存和需要使用 Redis 存储的数据两种内容的区别。
 * <li>缓存的使用场景是：对于相同的入参，某个方法的返回值预估在短期内不会发生变化，或者即便发生变化仍使用旧数据也无所谓的时候，使用缓存避免重复执行，
 * 提高运行效率。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2021-12-13
 * @since 1.0.0
 */
@Configuration
@RequiredArgsConstructor
public class SpringCacheConfig implements CachingConfigurer {
    private final RedisConnectionFactory redisConnectionFactory;

    @Override
    public CacheManager cacheManager() {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration
            .defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))
            .computePrefixWith(name -> name)
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return new CustomRedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory), defaultCacheConfig);
    }

    /**
     * 自定义键名规则
     * <p>
     * <h2>为什么要使用自定义键名规则？
     * <p>可以跨项目共享缓存数据，默认的规则有点怪异，并不通用，将其转化为通用格式。
     * <p>
     * <h2>为什么要跨项目共享缓存数据？
     * <p>例如获取指定经纬度的实时天气，是向第三方发送 HTTP 请求获取的数据，这个数据符合使用缓存的场景，除了在当前项目使用到外，
     * 笔者还在其他语言框架(Nest.js)的项目中使用。
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new MyKeyGenerator();
    }

    /**
     * 自定义键名生成器
     * <p>
     * <h2>规则
     * <p>先和键名使用 {@code :} 连接，然后每个参数之间使用 {@code ,} 连接。
     */
    public static class MyKeyGenerator implements KeyGenerator {
        @Override
        @SuppressWarnings("NullableProblems")
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
