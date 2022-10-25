package com.inlym.lifehelper.external.heweather;

/**
 * 和风天气网络地址辅助类
 *
 * <h2>主要用途
 * <p>将需要拼接 URL 地址的方法封装在此类中。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/26
 * @since 1.5.0
 **/
public abstract class HeUrlUtils {
    /** 存放和风天气图标的目录地址 */
    private static final String ICON_BASE_URL = "https://static.lifehelper.com.cn/qweather/icon/";

    /** 存放和风天气生活指数图片的目录地址 */
    private static final String LIVE_IMAGE_BASE_URL = "https://static.lifehelper.com.cn/qweather/live/";

    /** 存放和风天气灾害预警图片的目录地址 */
    private static final String WARNING_IMAGE_BASE_URL = "https://static.lifehelper.com.cn/qweather/warning/";

    /** 存放和风天气灾害预警图片的目录地址 */
    private static final String WARNING_ICON_BASE_URL = "https://static.lifehelper.com.cn/qweather/warning-icon/";

    /**
     * 获取天气 icon 图片的 URL 地址
     *
     * @param iconId icon 字段数据
     *
     * @since 1.5.0
     */
    public static String getIconUrl(String iconId) {
        return ICON_BASE_URL + iconId + ".png";
    }

    /**
     * 获取天气生活指数图片的 URL 地址
     *
     * @param typeId 生活指数类型 ID
     *
     * @since 1.5.0
     */
    public static String getLiveImageUrl(String typeId) {
        return LIVE_IMAGE_BASE_URL + typeId + ".svg";
    }

    /**
     * 获取天气预警图片的 URL 地址
     *
     * @param type  预警类型 ID
     * @param level 预警等级
     *
     * @since 1.5.0
     */
    public static String getWarningImageUrl(String type, String level) {
        // to do
        return null;
    }
}
