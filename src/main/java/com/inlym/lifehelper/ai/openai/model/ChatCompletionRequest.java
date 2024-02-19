package com.inlym.lifehelper.ai.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 会话补全接口请求数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/19
 * @see <a href="https://platform.openai.com/docs/api-reference/chat/create">Create chat completion</a>
 * @since 2.1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletionRequest {
    /** 要使用的模型的 ID */
    private String model;

    /** 对话所包含的消息列表 */
    private List<ChatCompletionMessage> messages;

    /**  */
    @JsonProperty("frequency_penalty")
    private Float frequencyPenalty;

    /**  */
    @JsonProperty("logit_bias")
    private Map<String, Integer> logitBias;

    /** 聊天完成时生成的最大令牌数 */
    @JsonProperty("max_tokens")
    private Integer maxTokens;

    /** 输出的聊天消息可选项数量 */
    private Integer n;

    /**  */
    @JsonProperty("presence_penalty")
    private Float presencePenalty;

    /** 响应格式 */
    @JsonProperty("response_format")
    private ResponseFormat responseFormat;

    /**  */
    private Integer seed;

    /**  */
    private List<String> stop;

    /**  */
    private Boolean stream;

    /**
     * 使用什么采样温度，介于 0 和 2 之间
     *
     * <h2>说明
     * <p>较高的值（如 0.8）将使输出更加随机，而较低的值（如 0.2）将使输出更加集中和确定。 我们通常建议改变这个或 top_p 但不是两者。
     */
    private Float temperature;

    /**
     * 一种替代温度采样的方法，称为核采样
     *
     * <h2>说明
     * <p>模型考虑具有 top_p 概率质量的标记的结果。所以 0.1 意味着只考虑构成前 10% 概率质量的标记。 我们通常建议改变这个或 temperature 但不是两者。
     */
    @JsonProperty("top_p")
    private Float topP;

    /**  */
    private String user;
}
