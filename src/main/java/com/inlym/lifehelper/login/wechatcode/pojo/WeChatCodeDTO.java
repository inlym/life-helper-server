package com.inlym.lifehelper.login.wechatcode.pojo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 通过小程序 code 登录的请求数据
 *
 * <h2>说明
 * <p>在小程序端，调用 `wx.login` 方法获取 `code`，直接将该 `code` 上传用于登录。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/4
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/open-api/login/wx.login.html">wx.login</a>
 * @since 1.3.0
 **/
@Data
public class WeChatCodeDTO {
    /** 微信登录凭证 */
    @NotEmpty
    private String code;
}
