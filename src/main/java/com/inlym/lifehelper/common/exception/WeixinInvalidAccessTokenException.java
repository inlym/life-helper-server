package com.inlym.lifehelper.common.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信服务端接口调用凭据失效异常
 * <p>
 * [说明]
 * 调用微信服务端绝大多数后台接口时都需使用接口调用凭据（access_token），但在一些情况下可能会出现接口调用凭据失效的情况，出现这种情况，
 * 则抛出当前异常。
 *
 * @author inlym
 * @since 2022-01-20 19:56
 **/
@ToString
public class WeixinInvalidAccessTokenException extends Exception {
    /** 错误状态码 */
    @Getter
    @Setter
    private Integer errCode;

    /** 错误消息 */
    @Getter
    @Setter
    private String errMsg;

    public WeixinInvalidAccessTokenException(Integer errCode, String errMsg) {
        super();

        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}
