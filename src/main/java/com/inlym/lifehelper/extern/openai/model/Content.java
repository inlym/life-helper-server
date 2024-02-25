package com.inlym.lifehelper.extern.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 类名称
 * <p>
 * <h2>主要用途
 * <p>
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/20
 * @since 2.1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    private String token;

    private Float logprob;

    @JsonProperty("bytes")
    private List<Integer> probBytes;

    @JsonProperty("top_logprobs")
    private List<TopLogProbs> topLogprobs;
}
