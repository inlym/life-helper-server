package com.weutil.weather.data.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基础天气数据
 *
 * <h2>主要用途
 * <p>用于一些只需要展示简单天气信息的地方。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/30
 * @since 1.5.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicWeather {
    /** 天气图标的 URL 地址 */
    private String iconUrl;

    /** 温度，默认单位：摄氏度 */
    private String temp;

    /** 天气状况的文字描述，包括阴晴雨雪等天气状态的描述 */
    private String text;

    public static BasicWeather from(WeatherNow now) {
        BasicWeather res = new BasicWeather();
        res.setIconUrl(now.getIconUrl());
        res.setTemp(now.getTemp());
        res.setText(now.getText());

        return res;
    }
}
