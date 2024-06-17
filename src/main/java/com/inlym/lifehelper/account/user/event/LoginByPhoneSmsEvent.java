package com.inlym.lifehelper.account.user.event;

import com.inlym.lifehelper.account.login.phone.entity.PhoneSmsLoginLog;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 使用手机号验证码登录事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/12
 * @since 2.3.0
 **/
@Getter
public class LoginByPhoneSmsEvent extends ApplicationEvent {
    private final PhoneSmsLoginLog phoneSmsLoginLog;

    /** 关联的用户手机号账户表 ID */
    private final Long userAccountPhoneId;

    /** 对应的用户 ID */
    private final Long userId;

    public LoginByPhoneSmsEvent(PhoneSmsLoginLog phoneSmsLoginLog) {
        super(phoneSmsLoginLog);

        this.phoneSmsLoginLog = phoneSmsLoginLog;
        this.userAccountPhoneId = phoneSmsLoginLog.getUserAccountPhoneId();
        this.userId = phoneSmsLoginLog.getUserId();
    }
}
