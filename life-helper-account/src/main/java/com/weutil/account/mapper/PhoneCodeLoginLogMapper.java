package com.weutil.account.mapper;

import com.mybatisflex.core.BaseMapper;
import com.weutil.account.entity.PhoneCodeLoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 手机号（短信验证码）登录日志表存储库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/24
 * @since 3.0.0
 **/
@Mapper
public interface PhoneCodeLoginLogMapper extends BaseMapper<PhoneCodeLoginLog> {}
