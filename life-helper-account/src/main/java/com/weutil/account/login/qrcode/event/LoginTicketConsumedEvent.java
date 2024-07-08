package com.weutil.account.login.qrcode.event;

import com.weutil.account.login.qrcode.entity.LoginTicket;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 扫码登录凭据消费使用事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/26
 * @since 2.2.0
 **/
@Getter
public class LoginTicketConsumedEvent extends ApplicationEvent {
    private final LoginTicket ticket;

    public LoginTicketConsumedEvent(LoginTicket source) {
        super(source);
        this.ticket = source;
    }
}
