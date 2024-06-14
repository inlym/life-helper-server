package com.inlym.lifehelper.account.user.event;

import com.inlym.lifehelper.account.user.model.LoginLog;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

/**
 * 用户登录事件
 *
 * <h2>说明
 * <p>当前事件是其他登录事件的父类。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/15
 * @since 2.3.0
 **/
@Getter
public class LoginEvent extends ApplicationEvent {
    /** 对应的用户 ID */
    private final Long userId;

    /** 客户端 IP 地址 */
    private final String ip;

    /** 登录时间 */
    private final LocalDateTime loginTime;

    public LoginEvent(LoginLog log) {
        super(log);

        this.userId = log.getUserId();
        this.ip = log.getIp();
        this.loginTime = log.getLoginTime();
    }
}
