package com.weutil.external.heweather.pojo;

import lombok.Data;

/**
 * 月升月落和月相响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/5/5
 * @see <a href="https://dev.qweather.com/docs/api/astronomy/moon-and-moon-phase/">月升月落和月相</a>
 * @since 1.2.1
 **/
@Data
public class HeMoonResponse {
    /** API 状态码 */
    private String code;

    /** 当前 API 的最近更新时间 */
    private String updateTime;

    /** 月升时间 */
    private String moonrise;

    /** 月落时间 */
    private String moonset;

    private MoonPhase[] moonPhase;

    @Data
    public static class MoonPhase {
        /** 月相逐小时预报时间 */
        private String fxTime;

        /** 月相数值 */
        private String value;

        /** 月相名字 */
        private String name;

        /** 月相图标代码 */
        private String icon;

        /** 月亮照明度，百分比数值 */
        private String illumination;
    }
}
