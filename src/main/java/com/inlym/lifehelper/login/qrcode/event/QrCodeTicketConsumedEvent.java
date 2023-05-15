package com.inlym.lifehelper.login.qrcode.event;

import com.inlym.lifehelper.login.qrcode.entity.QrCodeTicket;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 二维码凭据消费事件
 *
 * <h2>事件说明
 * <p>当二维码凭据被消费使用后，抛出此事件。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/5/16
 * @since 2.0.0
 **/
public class QrCodeTicketConsumedEvent extends ApplicationEvent {
    @Getter
    private QrCodeTicket qrCodeTicket;

    public QrCodeTicketConsumedEvent(Object source) {
        super(source);

        if (source instanceof QrCodeTicket ticket) {
            this.qrCodeTicket = ticket;
        }
    }
}
