package com.weutil.account.event;

import com.weutil.account.entity.LoginHistory;
import lombok.Getter;

/**
 * 使用手机短信验证码登录事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
@Getter
public class LoginByPhoneCodeEvent extends LoginEvent {

    /** 关联的用户手机号账户表 ID */
    private final Long phoneAccountId;

    public LoginByPhoneCodeEvent(LoginHistory source) {
        super(source);

        this.phoneAccountId = source.getPhoneAccountId();
    }
}
