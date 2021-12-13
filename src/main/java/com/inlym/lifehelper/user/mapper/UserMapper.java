package com.inlym.lifehelper.user.mapper;

import com.inlym.lifehelper.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    /**
     * 通过 openid 查找用户
     *
     * @param openid 微信小程序用户唯一标识
     */
    User findUserByOpenid(String openid);
}
