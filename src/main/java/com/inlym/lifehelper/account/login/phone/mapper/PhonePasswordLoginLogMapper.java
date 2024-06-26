package com.inlym.lifehelper.account.login.phone.mapper;

import com.inlym.lifehelper.account.login.phone.entity.PhonePasswordLoginLog;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 手机号+密码登录日志存储库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/15
 * @since 2.3.0
 **/
@Mapper
public interface PhonePasswordLoginLogMapper extends BaseMapper<PhonePasswordLoginLog> {}
