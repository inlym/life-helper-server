package com.inlym.lifehelper.account.user.event;

import com.inlym.lifehelper.account.user.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户创建事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @version 2.3.0
 * @since 2024/6/8
 **/
@Getter
public class UserCreatedEvent extends ApplicationEvent {
    private final User user;

    public UserCreatedEvent(User user) {
        super(user);
        this.user = user;
    }
}
