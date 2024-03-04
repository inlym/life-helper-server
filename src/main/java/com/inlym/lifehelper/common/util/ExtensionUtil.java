package com.inlym.lifehelper.common.util;

/**
 * 文件后缀名工具集
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/24
 * @since 2.2.0
 **/
public abstract class ExtensionUtil {
    /**
     * 检测图片格式
     *
     * @param bytes 图片字节内容
     *
     * @since 2.2.0
     */
    public static String detectImageFormat(byte[] bytes) {
        if (bytes.length >= 2 && bytes[0] == (byte) 0xFF && bytes[1] == (byte) 0xD8) {
            return "jpg";
        } else if (bytes.length >= 8 && bytes[0] == (byte) 0x89 && bytes[1] == (byte) 0x50 && bytes[2] == (byte) 0x4E && bytes[3] == (byte) 0x47 && bytes[4] == (byte) 0x0D && bytes[5] == (byte) 0x0A && bytes[6] == (byte) 0x1A && bytes[7] == (byte) 0x0A) {
            return "png";
        } else if (bytes.length >= 6 && bytes[0] == (byte) 0x47 && bytes[1] == (byte) 0x49 && bytes[2] == (byte) 0x46 && bytes[3] == (byte) 0x38 && bytes[4] == (byte) 0x39 && bytes[5] == (byte) 0x61) {
            return "gif";
        } else if (bytes.length >= 2 && bytes[0] == (byte) 0x42 && bytes[1] == (byte) 0x4D) {
            return "bmp";
        } else {
            return "";
        }
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
            if (mediaType.equals("image/gif")) {
                return "gif";
            }
            if (mediaType.equals("image/jpeg")) {
                return "jpg";
            }
            if (mediaType.equals("image/png")) {
                return "png";
            }
            if (mediaType.equals("image/webp")) {
                return "webp";
            }
            if (mediaType.equals("image/svg+xml")) {
                return "svg";
            }
            if (mediaType.equals("image/bmp")) {
                return "bmp";
            }
            if (mediaType.equals("image/tiff")) {
                return "tiff";
            }
        }

        throw new RuntimeException("未识别的媒体类型，mediaType=" + mediaType);
    }
}
