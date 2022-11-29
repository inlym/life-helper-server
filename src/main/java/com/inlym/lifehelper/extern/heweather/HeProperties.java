package com.inlym.lifehelper.extern.heweather;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 和风天气配置信息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-17
 * @see <a href="https://dev.qweather.com/docs/configuration/project-and-key/">项目和KEY</a>
 * @since 1.0.0
 **/
@Data
@Component
@ConfigurationProperties(prefix = "lifehelper.he-weather")
public class HeProperties {
    /** 密钥 */
    private String key;
}
