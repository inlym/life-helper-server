package com.inlym.lifehelper.user.account.service;

import com.inlym.lifehelper.user.account.entity.User;
import com.inlym.lifehelper.user.account.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户账户服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/4/16
 * @since 2.3.0
 **/
@Service
@RequiredArgsConstructor
public class UserAccountService {
    private final UserMapper userMapper;

    /**
     * 创建新用户
     *
     * @since 2.3.0
     */
    public long createUser() {
        User user = new User();
        userMapper.insertSelective(user);
        return user.getId();
    }
}
