package com.weutil.external.heweather.pojo;

import lombok.Data;

/**
 * 天气灾害预警响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/4/25
 * @see <a href="https://dev.qweather.com/docs/api/warning/weather-warning/">天气灾害预警</a>
 * @since 1.2.0
 **/
@Data
public class HeWarningNowResponse {
    /** API 状态码 */
    private String code;

    /** 当前 API 的最近更新时间 */
    private String updateTime;

    private WarningItem[] warning;

    @Data
    public static class WarningItem {
        /** 本条预警的唯一标识，可判断本条预警是否已经存在 */
        private String id;

        /** 预警发布单位，可能为空 */
        private String sender;

        /** 预警发布时间 */
        private String pubTime;

        /** 预警信息标题 */
        private String title;

        /** 预警开始时间，可能为空 */
        private String startTime;

        /** 预警结束时间，可能为空 */
        private String endTime;

        /** 预警信息的发布状态 */
        private String status;

        /** （该字段已弃用）预警等级，目前包含：白色、蓝色、绿色、黄色、橙色、红色、黑色 */
        @Deprecated
        private String level;

        /** 预警严重等级 */
        private String severity;

        /** 预警严重等级颜色，可能为空 */
        private String severityColor;

        /** 预警类型 ID */
        private String type;

        /** 预警类型名称 */
        private String typeName;

        /** 预警信息的紧迫程度，可能为空 */
        private String urgency;

        /** 预警信息的确定性，可能为空 */
        private String certainty;

        /** 预警详细文字描述 */
        private String text;

        /** 与本条预警相关联的预警 ID，当预警状态为 cancel 或 update 时返回。可能为空 */
        private String related;
    }
}
