package com.inlym.lifehelper.login.wechat.mapper;

import com.inlym.lifehelper.login.wechat.entity.WeChatLoginLog;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信登录记录日志存储库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/15
 * @since 2.1.0
 **/
@Mapper
public interface WeChatLoginLogMapper extends BaseMapper<WeChatLoginLog> {}
