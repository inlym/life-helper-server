package com.weutil.account.mapper;

import com.mybatisflex.core.BaseMapper;
import com.weutil.account.entity.LoginSmsTrace;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录短信验证码生命周期追踪表存储库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/24
 * @since 3.0.0
 **/
@Mapper
public interface LoginSmsTraceMapper extends BaseMapper<LoginSmsTrace> {}
