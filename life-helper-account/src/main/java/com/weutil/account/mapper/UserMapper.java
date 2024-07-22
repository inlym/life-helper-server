package com.weutil.account.mapper;

import com.mybatisflex.core.BaseMapper;
import com.weutil.account.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户账户存储库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {}
