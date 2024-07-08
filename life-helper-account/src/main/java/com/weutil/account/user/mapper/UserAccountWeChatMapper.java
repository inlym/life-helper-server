package com.weutil.account.user.mapper;

import com.mybatisflex.core.BaseMapper;
import com.weutil.account.user.entity.UserAccountWeChat;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户微信账户实体存储库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @since 2.3.0
 **/
@Mapper
public interface UserAccountWeChatMapper extends BaseMapper<UserAccountWeChat> {}
