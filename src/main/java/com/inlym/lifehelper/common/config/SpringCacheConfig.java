package com.inlym.lifehelper.common.config;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * Spring 缓存配置
 *
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

        return new CustomRedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                                           defaultCacheConfig);
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
     *
     * <h2>规则
     * <p>先和键名使用 {@code :} 连接，然后每个参数之间使用 {@code ,} 连接。
     *
     * <h2>备忘（2024/4/25）
     * <p>使用了 {@code key}，则不走下方生成规则，例如 {@code key = "':' + #user.id"}
     */
    public static class MyKeyGenerator implements KeyGenerator {
        @NotNull
        @Override
        public Object generate(@NotNull Object target, @NotNull Method method, Object... params) {
            if (params.length == 0) {
                return "";
            }

            // 使用“冒号”分隔键名和参数部分，使用“逗号”分隔各个参数
            return ":" + StringUtils.arrayToCommaDelimitedString(params);
        }
    }

    /**
     * 自定义 Redis 缓存管理器
     *
     * @author <a href="https://www.inlym.com">inlym</a>
     * @date 2022/5/8
     * @since 1.2.2
     **/
    public static class CustomRedisCacheManager extends RedisCacheManager {
        public CustomRedisCacheManager(RedisCacheWriter cacheWriter,
                                       RedisCacheConfiguration defaultCacheConfiguration) {
            super(cacheWriter, defaultCacheConfiguration);
        }

        @NotNull
        @Override
        protected RedisCache createRedisCache(@NotNull String name, RedisCacheConfiguration cacheConfig) {
            // 键名格式 `keyName#seconds`
            String[] strs = name.split("#");

            // 此处严格要求，必须按照以上格式，否则直接报错，便于开发阶段直接暴露问题
            Assert.isTrue(strs.length == 2, "缓存配置错误");

            String keyName = strs[0];
            long seconds = Long.parseLong(strs[1]);

            assert cacheConfig != null;
            return super.createRedisCache(keyName, cacheConfig.entryTtl(Duration.ofSeconds(seconds)));
        }
    }
}
