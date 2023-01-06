package com.inlym.lifehelper.login.scan.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 扫码登录操作请求数据
 *
 * <h2>数据说明
 * <p>扫码端（miniprogram）可做的操作包含：扫码、确认登录。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/1/7
 * @since 1.9.0
 **/
@Data
public class ScanLoginOperationDTO {
    /**
     * 操作类型
     *
     * <h2>可选值
     * <li> {@code scan} ：「扫码」操作
     * <li> {@code confirm} ：「确认登录」操作
     */
    @NotBlank
    private String type;
}
