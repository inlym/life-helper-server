package com.inlym.lifehelper.extern.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 会话补全接口请求数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/9/10
 * @see <a href="https://platform.openai.com/docs/api-reference/chat/create">创建会话补全</a>
 * @since 2.0.3
 **/
@Data
public class ChatCompletionRequestBody {
    /** 要使用的模型 */
    private String model;

    /** 消息列表 */
    private ChatCompletionMessage[] messages;

    /**
     * 采样温度
     * <p>
     * <h2>字段说明
     * <p>介于 0 和 2 之间。较高的值（如 0.8）将使输出更加随机，而较低的值（如 0.2）将使输出更加集中和确定。
     */
    private Integer temperature;

    /** 输出的聊天消息可选项数量 */
    private Integer n;

    /** 聊天完成时生成的最大令牌数 */
    @JsonProperty("max_tokens")
    private Integer maxTokens;
}
