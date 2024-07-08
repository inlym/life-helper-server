package com.weutil.external.heweather.pojo;

import lombok.Data;

/**
 * 日出日落响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/5/5
 * @see <a href="https://dev.qweather.com/docs/api/astronomy/sunrise-sunset/">日出日落</a>
 * @since 1.2.1
 **/
@Data
public class HeSunResponse {
    /** API 状态码 */
    private String code;

    /** 当前 API 的最近更新时间 */
    private String updateTime;

    /** 日出时间 */
    private String sunrise;

    /** 日落时间 */
    private String sunset;
}
