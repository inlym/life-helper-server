package com.inlym.lifehelper.login.scan.exception;

/**
 * 未找到扫码登录凭据异常
 *
 * <h2>主要用途
 * <p>当通过凭据 ID 查询时，对应平均查询无结果时（可能不存在或已使用），则抛出此异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/1/5
 * @since 1.9.0
 **/
public class ScanLoginTicketNotFoundException extends RuntimeException {
    public ScanLoginTicketNotFoundException(String message) {
        super(message);
    }

    public static ScanLoginTicketNotFoundException of(String id) {
        String message = "Invalid Ticket Id: " + id;
        return new ScanLoginTicketNotFoundException(message);
    }
}
