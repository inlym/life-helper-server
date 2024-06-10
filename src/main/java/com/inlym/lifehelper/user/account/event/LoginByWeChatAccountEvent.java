package com.inlym.lifehelper.user.account.event;

import com.inlym.lifehelper.login.wechat.entity.WeChatLogin;
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
    private final WeChatLogin weChatLogin;

    /** 关联的用户微信账户表 ID */
    private final Long userAccountWeChatId;

    /** 对应的用户 ID */
    private final Long userId;

    public LoginByWeChatAccountEvent(WeChatLogin source) {
        super(source);
        this.weChatLogin = source;
        this.userAccountWeChatId = source.getUserAccountWeChatId();
        this.userId = source.getUserId();
    }
}
