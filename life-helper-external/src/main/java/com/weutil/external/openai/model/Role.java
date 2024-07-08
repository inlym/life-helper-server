package com.weutil.external.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 对话角色
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/19
 * @since 2.1.0
 **/
public enum Role {

    @JsonProperty("system") SYSTEM,

    @JsonProperty("user") USER,

    @JsonProperty("assistant") ASSISTANT,

    @JsonProperty("tool") TOOL
}
