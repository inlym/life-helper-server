package com.inlym.lifehelper.extern.chatgpt.pojo;

import lombok.Data;

/**
 * 会话补全接口响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/25
 * @see <a href="https://platform.openai.com/docs/api-reference/completions/create">Create completion</a>
 * @since 1.9.5
 **/
@Data
public class CreateCompletionResponse {
    /** 会话的 ID */
    private String id;

    /** 接口名称 */
    private String object;

    /** 创建会话时间戳 */
    private String created;

    /** 使用的模型 */
    private String model;

    private Choice[] choices;

    /** 发生错误时的字段 */
    private ApiError error;

    @Data
    public static class Choice {
        /** 回复的文本 */
        private String text;

        /** 索引顺序 */
        private Integer index;
    }
}
