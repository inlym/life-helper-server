package com.weutil.common.startup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

/**
 * 时区设置任务
 *
 * <h2>说明
 * <p>在 Docker 中，默认使用了 GMT 时区，而项目运行时需要以 "Asia/Shanghai" 时区运行。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @since 3.0.0
 **/
@Component
@Slf4j
public class TimeZoneSettingRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        log.trace("[启动时任务] 已将时区设置为 Asia/Shanghai");
    }
}
