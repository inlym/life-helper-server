package com.inlym.lifehelper.extern.wechat.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 小程序登录响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/3
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-login/code2Session.html">小程序登录</a>
 * @since 1.3.0
 **/
@Data
public class WeChatCode2SessionResponse implements WeChatCommonResponse {
    /** 错误码 */
    @JsonProperty("errcode")
    private Integer errorCode;

    /** 错误信息 */
    @JsonProperty("errmsg")
    private String errorMessage;

    /** 用户唯一标识 */
    private String openId;

    /** 用户在微信开放平台的唯一标识符 */
    private String unionId;

    /** 会话密钥 */
    @JsonProperty("session_key")
    private String sessionKey;
}
