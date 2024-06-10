package com.inlym.lifehelper.user.account.event;

import com.inlym.lifehelper.login.wechat.entity.WeChatLoginLog;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 使用微信账户登录事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/9
 * @since 2.3.0
 **/
@Getter
public class LoginByWeChatAccountEvent extends ApplicationEvent {
    private final WeChatLoginLog weChatLoginLog;

    /** 关联的用户微信账户表 ID */
    private final Long userAccountWeChatId;

    /** 对应的用户 ID */
    private final Long userId;

    public LoginByWeChatAccountEvent(WeChatLoginLog source) {
        super(source);
        this.weChatLoginLog = source;
        this.userAccountWeChatId = source.getUserAccountWeChatId();
        this.userId = source.getUserId();
    }
}
