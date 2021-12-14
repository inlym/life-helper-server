package com.inlym.lifehelper.user.impl;

import com.inlym.lifehelper.user.UserService;
import com.inlym.lifehelper.user.entity.User;
import com.inlym.lifehelper.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {this.userMapper = userMapper;}

    /**
     * 通过 openid 获取用户 ID（用户不存在时将自动创建用户）
     *
     * @param openid 微信小程序用户唯一标识
     */
    @Override
    public int getUserIdByOpenid(String openid) {
        User user = userMapper.findUserByOpenid(openid);
        if (user != null) {
            return user.getId();
        } else {
            User user1 = new User();
            user1.setOpenid(openid);
            userMapper.insertWithOpenid(user1);
            return user1.getId();
        }
    }
}
