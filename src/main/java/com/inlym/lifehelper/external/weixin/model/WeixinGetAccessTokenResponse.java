package com.inlym.lifehelper.external.weixin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 微信获取小程序全局唯一后台接口调用凭据请求响应数据
 *
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/access-token/auth.getAccessToken.html">auth.getAccessToken</a>
 */
@Data
public class WeixinGetAccessTokenResponse {
    /**
     * 错误码
     */
    @JsonProperty("errcode")
    private Integer errCode;

    /**
     * 错误信息
     */
    @JsonProperty("errmsg")
    private String errMsg;

    /**
     * 获取到的凭证
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 凭证有效时间，单位：秒。目前是7200秒之内的值。
     */
    @JsonProperty("expires_in")
    private Integer expiresIn;
}
