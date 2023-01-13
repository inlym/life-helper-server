package com.inlym.lifehelper.login.scan.pojo;

import com.inlym.lifehelper.common.validation.SimpleUUID;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 扫码登录请求数据
 *
 * <h2>主要用途
 * <p>被扫码端（Web）检查登录状态，发起登录请求时的请求数据。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/1/7
 * @since 1.9.0
 **/
@Data
public class ScanLoginDTO {
    /** 扫码登录票据 ID */
    @NotNull
    @SimpleUUID
    private String id;
}
