package com.inlym.lifehelper.login.scan.exception;

/**
 * 扫码登录凭据非法操作异常
 *
 * <h2>以下操作为「非法」：
 * <li>对「已使用」的凭据做任何操作。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/1/5
 * @since 1.x.x
 **/
public class ScanLoginTicketInvalidOperationException extends RuntimeException {
    public ScanLoginTicketInvalidOperationException(String message) {
        super(message);
    }

    public static ScanLoginTicketInvalidOperationException of(String id) {
        String message = "Invalid Operation For id: " + id;
        return new ScanLoginTicketInvalidOperationException(message);
    }
}
