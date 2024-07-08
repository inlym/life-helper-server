package com.weutil.external.wechat.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 获取接口调用凭据响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/3
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/mp-access-token/getAccessToken.html">获取接口调用凭据</a>
 * @since 1.3.0
 **/
@Data
public class WeChatGetAccessTokenResponse implements WeChatCommonResponse {
    /** 错误码 */
    @JsonProperty("errcode")
    private Integer errorCode;

    /** 错误信息 */
    @JsonProperty("errmsg")
    private String errorMessage;

    /** 获取到的凭证 */
    @JsonProperty("access_token")
    private String accessToken;

    /** 凭证有效时间，单位：秒。目前是7200秒之内的值。 */
    @JsonProperty("expires_in")
    private Integer expiresIn;
}
