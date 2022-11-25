package com.inlym.lifehelper.user.mapper;

import com.inlym.lifehelper.user.entity.User;
import org.springframework.stereotype.Service;

/**
 * 临时替补类
 *
 * <h2>主要用途
 * <p>升级到 Spring Boot 3.0.0 版本，MyBatis 依赖本身存在问题，准确的讲，是 Spring Boot 3.0.0 中删除了 MyBatis 要使用的 `NestedIOException ` 导致错误。
 * 这里临时替换使得整个升级继续，后续再调整。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/25
 * @since 1.x.x
 **/
@Service
public class UserMapper {
    public User findById(int id) {
        System.out.println("版本升级依赖冲突临时方案，上线前记得替换");
        return new User();
    }

    public User findByOpenid(String openid) {
        System.out.println("版本升级依赖冲突临时方案，上线前记得替换");
        return new User();
    }

    public void insert(User user) {
        System.out.println("版本升级依赖冲突临时方案，上线前记得替换");
    }

    public void update(User user) {
        System.out.println("版本升级依赖冲突临时方案，上线前记得替换");
    }
}
