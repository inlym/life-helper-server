package com.inlym.lifehelper.user.account.event;

import com.inlym.lifehelper.user.account.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

/**
 * 新用户注册事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/26
 * @since 1.7.0
 **/
@Getter
public class NewUserRegistrationEvent extends ApplicationEvent {
    /** 用户 ID */
    private final Integer userId;

    /** 微信小程序用户唯一标识 */
    private final String openid;

    /** 注册时间 */
    private final LocalDateTime registerTime;

    /** 账户 ID */
    private final Integer accountId;

    public NewUserRegistrationEvent(User user) {
        super(user);

        this.userId = user.getId();
        this.openid = user.getOpenid();
        this.accountId = user.getAccountId();
        this.registerTime = user.getRegisterTime();
    }
}
