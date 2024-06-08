package com.inlym.lifehelper.common.base.aliyun.oss.constant;

import lombok.Getter;

/**
 * 在主存储空间（CentralBucket）使用的目录
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/8
 * @since 2.3.0
 **/
@Getter
public enum OssDir {
    /** 临时使用的目录，一般仅用于开发阶段调试 */
    TEMP("temp"),

    /** 用户头像 */
    AVATAR("avatar");

    /** 目录名 */
    private final String dirname;

    OssDir(String dirname) {
        this.dirname = dirname;
    }
}
