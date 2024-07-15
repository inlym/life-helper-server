package com.weutil.oss.model;

import lombok.Getter;

/**
 * 在 OSS 中使用的目录
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/15
 * @since 3.0.0
 **/
@Getter
public enum OssDir {
    /** 用户头像 */
    AVATAR("avatar"),

    /** 微信小程序码 */
    WEACODE("wxacode"),

    /** 用户上传使用 */
    UPLOAD("upload"),

    /** 临时使用的目录，一般仅用于开发阶段调试 */
    TEMP("temp");

    /** 目录名 */
    private final String dirname;

    OssDir(String dirname) {
        this.dirname = dirname;
    }
}
