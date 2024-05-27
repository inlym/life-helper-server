package com.inlym.lifehelper.common.util;

import lombok.SneakyThrows;
import org.apache.commons.imaging.Imaging;

/**
 * 图片工具集
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/5/27
 * @since 2.3.0
 **/
public abstract class ImageUtil {
    /**
     * 检测图片资源的默认后缀名
     *
     * @param bytes 图片资源
     *
     * @return 后缀名，返回格式示例：`png`, `jpg`, `gif`, `webp`
     * @date 2024/5/27
     * @since 2.3.0
     */
    @SneakyThrows
    public static String detectFormat(byte[] bytes) {
        return Imaging.guessFormat(bytes).getDefaultExtension();
    }

    /**
     * 根据媒体类型推断拓展名
     *
     * @param mediaType 媒体类型
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    public static String getExtensionFromMediaType(String mediaType) {
        if (mediaType != null) {
            switch (mediaType) {
                case "image/gif" -> {
                    return "gif";
                }
                case "image/jpeg" -> {
                    return "jpg";
                }
                case "image/png" -> {
                    return "png";
                }
                case "image/webp" -> {
                    return "webp";
                }
                case "image/svg+xml" -> {
                    return "svg";
                }
                case "image/bmp" -> {
                    return "bmp";
                }
                case "image/tiff" -> {
                    return "tiff";
                }
            }
        }

        throw new RuntimeException("未识别的媒体类型，mediaType=" + mediaType);
    }
}
