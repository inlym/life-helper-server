package com.inlym.lifehelper.common.base.aliyun.oss;

import java.util.UUID;

/**
 * 阿里云 OSS 辅助方法
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/13
 * @since 1.3.0
 **/
public abstract class OssUtils {
    /**
     * 获取随机文件名
     *
     * <h2>说明
     * <p>目前为去掉短横线的 UUID。
     *
     * @since 1.3.0
     */
    public static String getRandomFilename() {
        return UUID
            .randomUUID()
            .toString()
            .toLowerCase()
            .replaceAll("-", "");
    }
}
