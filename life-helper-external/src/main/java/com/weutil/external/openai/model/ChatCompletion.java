package com.weutil.external.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 会话补全接口响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/19
 * @since 2.1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletion {
    /** 唯一标识符 */
    private String id;

    /** 选项列表（如果n大于1，可以有多个选项） */
    private List<Choice> choices;

    /** 创建时间的时间戳（秒） */
    private Long created;

    /** 模型 */
    private String model;

    /** 该指纹表示模型运行的后端配置 */
    @JsonProperty("system_fingerprint")
    private String systemFingerprint;

    /** 对象类型，固定为 `chat.completion` */
    private String object;

    /** 使用统计信息 */
    private Usage usage;

    private ResponseError error;
}
