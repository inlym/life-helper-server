package com.inlym.lifehelper.user.info.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户所在地区封装对象
 *
 * <h2>说明
 * <p>用户地区只精确到第一和第二级行政区划。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/3
 * @since 1.7.2
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegionBO {
    /** 第1级行政区划的 adcode */
    private Integer admin1Id;

    /** 第1级行政区划的简称 */
    private String admin1ShortName;

    /** 第2级行政区划的 adcode */
    private Integer admin2Id;

    /** 第2级行政区划的简称 */
    private String admin2ShortName;
}
