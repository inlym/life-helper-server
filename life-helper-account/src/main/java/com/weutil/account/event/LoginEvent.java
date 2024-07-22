package com.weutil.account.event;

import com.weutil.account.model.LoginLog;
import lombok.Getter;
import lombok.Setter;
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
@Setter
public class LoginEvent extends ApplicationEvent implements LoginLog {
    /** 对应的用户 ID */
    private Long userId;

    /** 客户端 IP 地址 */
    private String ip;

    /** 登录时间 */
    private LocalDateTime loginTime;

    public LoginEvent(Object object) {
        super(object);
    }
}
