package com.inlym.lifehelper.weather.data.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 天气数据整合
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/30
 * @since 1.5.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDataVO {
    private WeatherNow now;

    private List<WeatherDaily> daily;

    private List<WeatherHourly> hourly;

    private MinutelyRain rain;

    private List<LivingIndex> indices;

    private List<WarningNow> warnings;

    private AirNow airNow;

    private List<AirDaily> airDaily;

    /** 用于展示的地点名称 */
    private String locationName;
}
