package com.inlym.lifehelper.login.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author inlym
 * @date 2022-02-09 23:37
 **/
@Data
public class LoginByCodeDTO {
    /** 微信登录凭证 */
    @NotEmpty
    private String code;
}
