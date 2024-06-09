package com.inlym.lifehelper.user.account.event;

import com.inlym.lifehelper.user.account.entity.UserAccountWeChat;
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
    private final UserAccountWeChat userAccountWeChat;

    public LoginByWeChatAccountEvent(UserAccountWeChat source) {
        super(source);
        this.userAccountWeChat = source;
    }
}
