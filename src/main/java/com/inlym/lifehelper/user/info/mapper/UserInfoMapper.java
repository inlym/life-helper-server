package com.inlym.lifehelper.user.info.mapper;

import com.inlym.lifehelper.user.info.entity.UserInfo;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息存储库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/16
 * @since 2.1.0
 **/
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {}
