package com.inlym.lifehelper.login.scanlogin.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于扫码登录时的请求数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/29
 * @since 1.3.0
 **/
@Data
@NoArgsConstructor
public class ScanLoginDTO {
    /** 登录凭证 ID */
    private String id;
}
