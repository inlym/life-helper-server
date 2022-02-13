package com.inlym.lifehelper.user.mapper;

import com.inlym.lifehelper.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author inlym
 * @date 2022-01-23 00:07
 */
@Mapper
public interface UserMapper {
    /**
     * 通过用户 ID 查找用户
     *
     * @param id 主键 ID
     *
     * @return 用户实体
     */
    User findById(int id);

    /**
     * 通过 openid 查找用户
     *
     * @param openid 微信小程序用户唯一标识
     *
     * @return 用户实体
     */
    User findByOpenid(String openid);

    /**
     * 通过 openid 创建新用户
     *
     * @param user 用户实体
     *
     * @return 新创建用户的用户 ID
     */
    int insertWithOpenid(@Param("user") User user);

    /**
     * 更新用户信息
     *
     * @param user 用户实体
     */
    void update(@Param("user") User user);
}
