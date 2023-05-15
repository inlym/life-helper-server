package com.inlym.lifehelper.login.qrcode.exception;

/**
 * 未找到二维码登录凭据异常
 *
 * <h2>主要用途
 * <p>当通过凭据 ID 查询时，查询无结果时（可能不存在或已使用），则抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/5/15
 * @since 2.0.0
 **/
public class QrCodeTicketNotFoundException extends RuntimeException {
    public QrCodeTicketNotFoundException(String id) {
        super("Invalid Ticket Id: " + id);
    }
}
