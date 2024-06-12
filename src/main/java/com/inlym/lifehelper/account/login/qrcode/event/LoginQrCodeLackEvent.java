package com.inlym.lifehelper.account.login.qrcode.event;

import org.springframework.context.ApplicationEvent;

/**
 * 小程序码不足事件
 *
 * <h2>主要用途
 * <p>当预留的小程序码不足时，抛出此事件，用于再批量生成一批。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/26
 * @since 2.2.0
 **/
public class LoginQrCodeLackEvent extends ApplicationEvent {
    public LoginQrCodeLackEvent(Object source) {
        super(source);
    }
}
