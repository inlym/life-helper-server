package com.inlym.lifehelper.login.pojo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 微信登录请求数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-09
 * @since 1.0.0
 **/
@Data
public class WeixinCodeDTO {
    /** 微信登录凭证 */
    @NotEmpty
    private String code;
}
