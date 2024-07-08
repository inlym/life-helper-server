package com.weutil.external.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图片对象
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/22
 * @see <a href="https://platform.openai.com/docs/api-reference/images/object">The image object</a>
 * @since 2.2.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageData {
    private String url;

    @JsonProperty("b64_json")
    private String b64Json;

    @JsonProperty("revised_prompt")
    private String revisedPrompt;
}
