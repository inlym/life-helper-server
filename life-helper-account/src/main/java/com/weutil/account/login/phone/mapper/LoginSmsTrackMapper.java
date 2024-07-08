package com.weutil.account.login.phone.mapper;

import com.mybatisflex.core.BaseMapper;
import com.weutil.account.login.phone.entity.LoginSmsTrack;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录短信验证码生命周期追踪表村出库
 *
 * <h2>说明
 * <p>说明文本内容
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/15
 * @since 2.3.0
 **/
@Mapper
public interface LoginSmsTrackMapper extends BaseMapper<LoginSmsTrack> {}
