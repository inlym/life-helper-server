package com.inlym.lifehelper.external.weixin.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 微信服务端登录凭证校验响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-23
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html">auth.code2Session</a>
 * @since 1.0.0
 */
@Data
public class WeixinCode2SessionResponse {
    /** 错误码 */
    @JsonProperty("errcode")
    private Integer errCode;

    /** 错误信息 */
    @JsonProperty("errmsg")
    private String errMsg;

    /** 用户唯一标识 */
    private String openid;

    /** 用户在开放平台的唯一标识符 */
    private String unionid;

    /** 会话密钥 */
    @JsonProperty("session_key")
    private String sessionKey;
}
