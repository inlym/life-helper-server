package com.weutil.common.startup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

/**
 * 设置时区
 *
 * <h2>说明
 * <p>在 Docker 中，默认使用了 GMT 时区，而项目运行时需要以 "Asia/Shanghai" 时区运行。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/5
 * @since 1.9.4
 **/
@Component
@Slf4j
public class SettingTimeZoneRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }
}
