package com.weutil.account.login.common.event;

import com.weutil.account.login.phone.entity.PhoneSmsLoginLog;
import lombok.Getter;

/**
 * 使用手机号验证码登录事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/12
 * @since 2.3.0
 **/
@Getter
public class LoginByPhoneSmsEvent extends LoginEvent {
    private final PhoneSmsLoginLog phoneSmsLoginLog;

    /** 关联的用户手机号账户表 ID */
    private final Long userAccountPhoneId;

    public LoginByPhoneSmsEvent(PhoneSmsLoginLog phoneSmsLoginLog) {
        super(phoneSmsLoginLog);

        this.phoneSmsLoginLog = phoneSmsLoginLog;
        this.userAccountPhoneId = phoneSmsLoginLog.getUserAccountPhoneId();

        this.setUserId(phoneSmsLoginLog.getUserId());
        this.setIp(phoneSmsLoginLog.getIp());
        this.setLoginTime(phoneSmsLoginLog.getLoginTime());
    }
}
