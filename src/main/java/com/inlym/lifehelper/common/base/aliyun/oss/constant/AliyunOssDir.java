package com.inlym.lifehelper.common.base.aliyun.oss.constant;

import lombok.Getter;

/**
 * 阿里云 OSS 目录
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/24
 * @since 2.2.0
 **/
@Getter
public enum AliyunOssDir {
    /** 临时使用的目录，一般仅用于开发阶段调试 */
    TEMP("temp"),

    /** 用户头像 */
    AVATAR("avatar"),

    /** 微信小程序码 */
    WEACODE("wxacode"),

    /** 用于客户端直传 */
    CLIENT_DIRECT_TRANSMISSION("upload");

    private final String dirname;

    AliyunOssDir(String dirname) {
        this.dirname = dirname;
    }
}
