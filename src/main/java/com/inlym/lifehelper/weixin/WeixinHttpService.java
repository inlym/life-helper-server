package com.inlym.lifehelper.weixin;

import com.inlym.lifehelper.weixin.model.WeixinCode2SessionResult;

public interface WeixinHttpService {
    /**
     * 通过 code 获取鉴权信息
     *
     * @param code 小程序端通过 wx.login 获取的 code
     *
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html">auth.code2Session</a>
     */
    WeixinCode2SessionResult code2Session(String code);
}
