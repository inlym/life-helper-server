package com.inlym.lifehelper.common.constant;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Redis 缓存收集器
 *
 * <h2>主要用途
 * <p>管理 Redis 缓存的键名和有效期
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/5/9
 * @since 1.2.2
 **/
public abstract class RedisCacheCollector {
    /** 通过 code 获取鉴权信息的键名 */
    public static final String WECHAT_SESSION = "wechat:session";

    // ===============================  腾讯位置服务  ===============================

    /** 腾讯位置服务 - IP 定位 */
    public static final String WE_MAP_LOCATE_IP = "wemap:locate-ip";

    /** 腾讯位置服务 - 逆地址解析（坐标位置描述） */
    public static final String WE_MAP_REVERSE_GEOCODE = "wemap:regeo";

    // ================================  和风天气  ================================

    /** 和风天气 - 城市信息查询 */
    public static final String HE_GEO_CITY_LOOKUP = "hefeng:geo";

    /** 和风天气 - 实时天气 */
    public static final String HE_WEATHER_NOW = "hefeng:now";

    /** 和风天气 - 逐天天气预报 */
    public static final String HE_WEATHER_DAILY = "hefeng:daily";

    /** 和风天气 - 逐小时天气预报 */
    public static final String HE_WEATHER_HOURLY = "hefeng:hourly";

    /** 和风天气 - 分钟级降水 */
    public static final String HE_WEATHER_MINUTELY = "hefeng:minutely";

    /** 和风天气 - 格点实时天气 */
    public static final String HE_GRID_WEATHER_NOW = "hefeng:grid-now";

    /** 和风天气 - 格点逐天天气预报 */
    public static final String HE_GRID_WEATHER_DAILY = "hefeng:grid-daily";

    /** 和风天气 - 格点逐小时天气预报 */
    public static final String HE_GRID_WEATHER_HOURLY = "hefeng:grid-hourly";

    /** 和风天气 - 天气灾害预警 */
    public static final String HE_WARNING_NOW = "hefeng:warning-now";

    /** 和风天气 - 天气预警城市列表 */
    public static final String HE_WARNING_LIST = "hefeng:warning-list";

    /** 和风天气 - 天气生活指数 */
    public static final String HE_INDICES_DAILY = "hefeng:indices";

    /** 和风天气 - 实时空气质量 */
    public static final String HE_WEATHER_AIR_NOW = "hefeng:air-now";

    /** 和风天气 - 空气质量预报 */
    public static final String HE_WEATHER_AIR_DAILY = "hefeng:air-daily";

    public static final Map<String, Duration> CACHE_DURATION_MAP = new HashMap<>(128) {{
        // =======================  微信小程序服务端 HTTP 请求缓存  =======================
        put(WECHAT_SESSION, Duration.ofDays(2));

        // ========================  腾讯位置服务 HTTP 请求缓存  ========================
        put(WE_MAP_LOCATE_IP, Duration.ofDays(30));
        put(WE_MAP_REVERSE_GEOCODE, Duration.ofDays(30));

        // =========================  和风天气 HTTP 请求缓存  =========================
        put(HE_GEO_CITY_LOOKUP, Duration.ofDays(100));
        put(HE_WEATHER_NOW, Duration.ofMinutes(10));
        put(HE_WEATHER_DAILY, Duration.ofHours(2));
        put(HE_WEATHER_HOURLY, Duration.ofMinutes(10));
        put(HE_GRID_WEATHER_NOW, Duration.ofMinutes(10));
        put(HE_GRID_WEATHER_DAILY, Duration.ofHours(2));
        put(HE_GRID_WEATHER_HOURLY, Duration.ofMinutes(10));
        put(HE_WARNING_NOW, Duration.ofMinutes(10));
        put(HE_WARNING_LIST, Duration.ofMinutes(5));
        put(HE_WEATHER_MINUTELY, Duration.ofMinutes(5));
        put(HE_INDICES_DAILY, Duration.ofHours(4));
        put(HE_WEATHER_AIR_NOW, Duration.ofMinutes(10));
        put(HE_WEATHER_AIR_DAILY, Duration.ofHours(1));
    }};
}
