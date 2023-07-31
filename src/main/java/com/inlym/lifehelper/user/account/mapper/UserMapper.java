package com.inlym.lifehelper.user.account.mapper;

import com.inlym.lifehelper.user.account.entity.User;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户账户存储库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/7/31
 * @since 2.0.2
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {}
