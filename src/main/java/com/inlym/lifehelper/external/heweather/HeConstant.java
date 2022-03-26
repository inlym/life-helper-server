package com.inlym.lifehelper.external.heweather;

/**
 * 和风天气中的常量
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/3/26
 **/
public class HeConstant {
    /** 开发版 API 请求地址前缀 */
    public static final String DEV_API_BASE_URL = "https://devapi.qweather.com/v7";

    /** 商业版 API 请求地址前缀 */
    public static final String PRO_API_BASE_URL = "https://api.qweather.com/v7";

    /** 表示请求成功的 `code` 值 */
    public static final String SUCCESS_CODE = "200";

    /** 存放和风天气图标的目录地址 */
    public static final String ICON_BASE_URL = "https://static.lifehelper.com.cn/qweather/icon/";

    /** 逐天天气预报可查询天数 */
    public static final class WeatherDailyDays {
        /** 3天 */
        public static final String DAYS_3 = "3d";

        /** 7天 */
        public static final String DAYS_7 = "7d";

        /** 10天 */
        public static final String DAYS_10 = "10d";

        /** 15天 */
        public static final String DAYS_15 = "15d";
    }

    /** 逐天天气预报可查询天数 */
    public static final class WeatherHourlyHours {
        /** 24小时 */
        public static final String Hours_24 = "24h";

        /** 72小时 */
        public static final String Hours_72 = "72h";

        /** 168小时 */
        public static final String Hours_168 = "168h";
    }
}
