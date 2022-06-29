package com.inlym.lifehelper.login.scanlogin.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 扫码登录结果
 *
 * <h2>说明
 * <p>用于 Web 端
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/29
 * @since 1.x.x
 **/
@Data
@NoArgsConstructor
public class ScanLoginResultVO {
    /** 是否已操作“扫码” */
    private Boolean scanned;

    /** 是否已操作“确认登录” */
    private Boolean confirmed;
    
    private String token;
}
