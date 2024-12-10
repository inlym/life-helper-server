package com.weutil.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 服务器信息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/4
 * @since 3.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerInfo {
    /** 最近一次项目启动的时间 */
    private LocalDateTime launchTime;

    /** 服务器的当前时间 */
    private LocalDateTime now;

    /** 本次项目启动后运行时长（单位：秒） */
    private Long duration;

    /** 当前激活的配置文件名称 */
    private String activeProfiles;

    /** 当前使用的端口号 */
    private String serverPort;

    /** 当前使用的 Spring Boot 版本号 */
    private String springBootVersion;

    /** 主机名 */
    private String hostName;

    /** IP 地址 */
    private String ip;

    /** 时区 */
    private String timeZone;

    /** 各中间件延迟时间（单位：毫秒） */
    private Map<String, Long> delay;
}
