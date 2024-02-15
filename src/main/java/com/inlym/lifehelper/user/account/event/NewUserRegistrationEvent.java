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
    private final Long userId;

    /** 用户在微信开放平台的唯一标识符 */
    private final String unionId;

    /** 注册时间 */
    private final LocalDateTime registerTime;

    public NewUserRegistrationEvent(User user) {
        super(user);

        this.userId = user.getId();
        this.unionId = user.getUnionId();
        this.registerTime = user.getRegisterTime();
    }
}
