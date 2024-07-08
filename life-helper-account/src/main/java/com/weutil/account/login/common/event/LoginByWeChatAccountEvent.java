package com.weutil.account.login.common.event;

import com.weutil.account.login.wechat.entity.WeChatLoginLog;
import lombok.Getter;

/**
 * 使用微信账户登录事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/9
 * @since 2.3.0
 **/
@Getter
public class LoginByWeChatAccountEvent extends LoginEvent {
    private final WeChatLoginLog weChatLoginLog;

    /** 关联的用户微信账户表 ID */
    private final Long userAccountWeChatId;

    public LoginByWeChatAccountEvent(WeChatLoginLog weChatLoginLog) {
        super(weChatLoginLog);

        this.weChatLoginLog = weChatLoginLog;
        this.userAccountWeChatId = weChatLoginLog.getUserAccountWeChatId();

        this.setUserId(weChatLoginLog.getUserId());
        this.setIp(weChatLoginLog.getIp());
        this.setLoginTime(weChatLoginLog.getLoginTime());
    }
}
