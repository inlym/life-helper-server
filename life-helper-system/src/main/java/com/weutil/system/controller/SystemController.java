package com.weutil.system.controller;

import com.weutil.system.model.ServerInfo;
import com.weutil.system.service.DelayTimeService;
import com.weutil.system.service.LaunchTimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统信息控制器
 *
 * <h2>主要用途
 * <p>查看系统的运行状态。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/4
 * @since 3.0.0
 **/
@RestController
@Slf4j
@RequiredArgsConstructor
public class SystemController {
    private final Environment environment;
    private final LaunchTimeService launchTimeService;
    private final DelayTimeService delayTimeService;

    /**
     * 查看系统服务器运行信息
     *
     * @date 2024/12/4
     * @since 3.0.0
     */
    @GetMapping("/debug/system/server")
    public ServerInfo getServerInfo() {
        // 项目启动时间及运行时长
        LocalDateTime launchTime = launchTimeService.getLaunchTime();
        LocalDateTime now = LocalDateTime.now();
        long duration = Duration.between(launchTime, now).toSeconds();

        // 各个中间件延迟时间
        Map<String, Long> delay = new HashMap<>();
        delay.put("mysql", delayTimeService.calcMysqlDelayTime());
        delay.put("redis", delayTimeService.calcRedisDelayTime());

        // CI 部署相关参数
        String commitId = System.getenv("CI_COMMIT_SHA");
        String commitRefName = System.getenv("CI_COMMIT_REF_NAME");

        ServerInfo info = ServerInfo.builder()
            .now(now)
            .launchTime(launchTime)
            .duration(duration)
            .activeProfiles(environment.getProperty("spring.profiles.active"))
            .serverPort(environment.getProperty("server.port"))
            .springBootVersion(SpringBootVersion.getVersion())
            .timeZone(ZoneId.systemDefault().getId())
            .delay(delay)
            .commitId(commitId)
            .commitRefName(commitRefName)
            .build();

        // 此处使用 `try...catch` 原因说明
        // 发生错误时，保证上述信息输出即可，无需由全局异常捕获器接管处理
        try {
            InetAddress localHost = Inet4Address.getLocalHost();
            info.setHostName(localHost.getHostName());
            info.setIp(localHost.getHostAddress());
        } catch (UnknownHostException e) {
            log.error("获取主机信息失败");
        }

        return info;
    }
}
