package com.inlym.lifehelper.account.login.qrcode.event;

import com.inlym.lifehelper.account.login.qrcode.entity.LoginTicket;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 扫码登录凭据创建事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/26
 * @since 2.2.0
 **/
@Getter
public class LoginTicketCreatedEvent extends ApplicationEvent {
    private final LoginTicket ticket;

    public LoginTicketCreatedEvent(LoginTicket source) {
        super(source);
        this.ticket = source;
    }
}
