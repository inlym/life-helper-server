package com.weutil.account.user.mapper;

import com.mybatisflex.core.BaseMapper;
import com.weutil.account.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户账户存储库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/4/16
 * @since 2.3.0
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {}
