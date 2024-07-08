package com.weutil.weather.data.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

/**
 * 星球（在天气模块特指太阳和月亮）
 *
 * <h2>主要用途
 * <p>封装日出日落、月初月落对象
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/29
 * @since 1.5.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Star {
    /** 升起时间，指日出和月出 */
    @JsonFormat(pattern = "H:mm")
    private LocalTime riseTime;

    /** 落下时间，指日落和月落 */
    @JsonFormat(pattern = "H:mm")
    private LocalTime setTime;
}
