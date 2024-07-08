package com.weutil.external.heweather.pojo;

import lombok.Data;

/**
 * 和风天气天气生活指数响应数据
 *
 * @author inlym
 * @date 2022-02-17
 * @see <a href="https://dev.qweather.com/docs/api/indices/">天气生活指数</a>
 **/
@Data
public class HeIndicesResponse {
    /** API 状态码 */
    private String code;

    /** 当前 API 的最近更新时间 */
    private String updateTime;

    private Daily[] daily;

    @Data
    public static class Daily {
        /** 预报日期 */
        private String date;

        /** 生活指数类型ID */
        private String type;

        /** 生活指数类型的名称 */
        private String name;

        /** 生活指数预报等级 */
        private String level;

        /** 生活指数预报级别名称 */
        private String category;

        /** 生活指数预报的详细描述，可能为空 */
        private String text;
    }
}
