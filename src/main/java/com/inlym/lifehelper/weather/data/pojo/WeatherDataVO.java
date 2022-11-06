package com.inlym.lifehelper.weather.data.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    /** 用于展示的地点名称 */
    private String locationName;

    /** 当前的日期 */
    @JsonFormat(pattern = "M月d日")
    private LocalDate date;
}
