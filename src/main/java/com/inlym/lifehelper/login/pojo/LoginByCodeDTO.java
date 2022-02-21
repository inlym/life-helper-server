package com.inlym.lifehelper.login.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 微信登录请求数据
 *
 * @author inlym
 * @date 2022-02-09
 **/
@Data
public class LoginByCodeDTO {
    /** 微信登录凭证 */
    @ApiModelProperty("从微信获取的 code")
    @NotEmpty
    private String code;
}
