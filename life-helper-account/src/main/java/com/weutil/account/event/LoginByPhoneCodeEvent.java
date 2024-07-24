package com.weutil.account.event;

import com.weutil.account.entity.PhoneCodeLoginLog;
import lombok.Getter;

/**
 * 使用手机号验证码登录事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
@Getter
public class LoginByPhoneCodeEvent extends LoginEvent {
    private final PhoneCodeLoginLog phoneCodeLoginLog;

    /** 关联的用户手机号账户表 ID */
    private final Long phoneAccountId;

    public LoginByPhoneCodeEvent(PhoneCodeLoginLog phoneCodeLoginLog) {
        super(phoneCodeLoginLog);

        this.phoneCodeLoginLog = phoneCodeLoginLog;
        this.phoneAccountId = phoneCodeLoginLog.getPhoneAccountId();

        this.setUserId(phoneCodeLoginLog.getUserId());
        this.setIp(phoneCodeLoginLog.getIp());
        this.setLoginTime(phoneCodeLoginLog.getLoginTime());
    }
}
