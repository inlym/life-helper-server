package com.inlym.lifehelper.login.scanlogin.pojo;

import lombok.Data;

/**
 * 登录凭证
 *
 * <h2>说明
 * <p>用于客户端获取扫码登录的凭证
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/27
 * @since 1.x.x
 **/
@Data
public class LoginTicketVO {
    /** 凭证编号 */
    private String id;

    /** 小程序码图片地址 */
    private String imageUrl;
}
