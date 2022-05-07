package com.inlym.lifehelper.common.constant;

/**
 * 特殊的路径
 *
 * <h2>说明
 * <p>项目内有一些路径有特殊用途，单独在此记录。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/5/7
 * @since 1.2.2
 **/
public class SpecialPath {
    /**
     * 用于健康检查的路径
     *
     * <h2>说明
     * <p>用于负载均衡进行健康检查，请求频率大致为 1次/秒，该路径的请求注意不要进行任何操作。
     */
    public final static String HEALTH_CHECK_PATH = "/ping";
}
