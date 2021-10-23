package com.inlym.lifehelper.weixin;

import com.inlym.lifehelper.weixin.model.Code2SessionResponse;

public interface WeixinHttpService {
    /**
     * 通过 code 获取鉴权信息
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html">auth.code2Session</a>
     *
     * @param code 小程序端通过 wx.login 获取的 code
     */
    Code2SessionResponse code2Session(String code);
}
