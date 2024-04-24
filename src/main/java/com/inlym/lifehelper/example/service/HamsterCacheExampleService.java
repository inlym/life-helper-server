package com.inlym.lifehelper.example.service;

import cn.hutool.core.util.IdUtil;
import com.inlym.lifehelper.example.entity.Hamster;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 缓存示例服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/4/24
 * @since 2.3.0
 **/
@Service
@Slf4j
public class HamsterCacheExampleService {

    @Cacheable("example:hamster#3600")
    public Hamster getHamsterById(long id) {
        log.debug("[getHamsterById] id={}", id);
        return Hamster.builder().id(id).name(IdUtil.objectId()).build();
    }

    @Cacheable("example:hamster#3600")
    public Hamster getHamster(long id, String name, int age) {
        log.debug("[getHamster] id={},name={},age={}", id, name, age);
        return Hamster.builder().id(id).name(name).age(age).build();
    }

    @CacheEvict("example:hamster#3600")
    public void deleteHamsterById(long id) {
        log.debug("[deleteHamsterById] id={}", id);
    }

    @CachePut(cacheNames = "example:hamster#3600", key = "':' + #hamster.id")
    public Hamster updateHamster(Hamster hamster) {
        log.debug("[updateHamster] hamster={}", hamster);
        return hamster;
    }
}
