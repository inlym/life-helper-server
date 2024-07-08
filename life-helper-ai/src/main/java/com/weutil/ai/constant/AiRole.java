package com.weutil.ai.constant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 角色
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/25
 * @since 2.2.0
 **/
@Getter
public enum AiRole {
    @JsonProperty("system") SYSTEMj("system"),

    @JsonProperty("user") USER("user"),

    @JsonProperty("assistant") ASSISTANT("assistant");

    @EnumValue
    private final String roleName;

    AiRole(String roleName) {
        this.roleName = roleName;
    }
}
