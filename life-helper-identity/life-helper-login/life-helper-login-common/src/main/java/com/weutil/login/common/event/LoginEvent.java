package com.weutil.login.common.event;

import com.weutil.login.common.entity.LoginLog;
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
 * @date 2024/7/22
 * @since 3.0.0
 **/
@Getter
public class LoginEvent extends ApplicationEvent {
    /** 原始登录日志 */
    private final LoginLog loginLog;
    
    /** 对应的用户 ID */
    private final Long userId;

    /** 客户端 IP 地址 */
    private final String ip;

    /** 登录时间 */
    private final LocalDateTime loginTime;

    public LoginEvent(LoginLog source) {
        super(source);

        this.userId = source.getUserId();
        this.ip = source.getIp();
        this.loginTime = source.getLoginTime();
        this.loginLog = source;
    }
}
