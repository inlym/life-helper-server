package com.inlym.lifehelper.weather.data.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 风
 *
 * <h2>主要用途
 * <p>封装跟“风”有关的一些属性
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/29
 * @since 1.5.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wind {
    /** 风向 360 角度，示例："45" */
    private String angle;

    /** 风向，示例："东北风" */
    private String direction;

    /** 风力等级，示例："3-4" */
    private String scale;

    /** 风速（km/h），示例："16" */
    private String speed;
}
