package com.inlym.lifehelper.external.heweather;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 和风天气配置文件
 *
 * @author inlym
 * @date 2022-02-17
 * @see <a href="https://dev.qweather.com/docs/resource/get-key/">创建应用和KEY</a>
 **/
@Data
@Component
@ConfigurationProperties(prefix = "lifehelper.he-weather")
public class HeWeatherProperties {
    /** 开发版密钥 */
    private String devKey;

    /** 商业版密钥 */
    private String proKey;
}
