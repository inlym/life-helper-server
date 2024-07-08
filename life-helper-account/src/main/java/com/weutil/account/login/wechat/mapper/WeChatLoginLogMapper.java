package com.weutil.account.login.wechat.mapper;

import com.mybatisflex.core.BaseMapper;
import com.weutil.account.login.wechat.entity.WeChatLoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信登录记录日志存储库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/10
 * @since 2.3.0
 **/
@Mapper
public interface WeChatLoginLogMapper extends BaseMapper<WeChatLoginLog> {}
