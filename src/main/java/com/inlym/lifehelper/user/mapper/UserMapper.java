package com.inlym.lifehelper.user.mapper;

import com.inlym.lifehelper.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    /**
     * 通过 openid 查找用户
     *
     * @param openid 微信小程序用户唯一标识
     */
    User findUserByOpenid(String openid);

    /**
     * 通过 openid 创建新用户
     *
     * @param user 用户实体
     */
    int insertWithOpenid(@Param("user") User user);
}
