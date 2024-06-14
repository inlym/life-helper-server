package com.inlym.lifehelper.account.login.wechat.mapper;

import com.inlym.lifehelper.account.login.wechat.entity.WeChatLoginLog;
import com.mybatisflex.core.BaseMapper;
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
