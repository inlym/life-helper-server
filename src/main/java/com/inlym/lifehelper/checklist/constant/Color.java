package com.inlym.lifehelper.checklist.constant;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 颜色
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/2
 * @since 2.3.0
 */
@Getter
public enum Color {
    /** 绿色（默认值） */
    GREEN(0, "绿色", "06ae56"),

    /** 红色 */
    RED(1, "红色", "fa5151"),

    /** 橘黄色 */
    ORANGE(2, "橙色", "fa9d3b"),

    /** 黄色 */
    YELLOW(3, "黄色", "ffc300"),

    /** 蓝色 */
    BLUE(4, "蓝色", "10aeff"),

    /** 靛蓝色 */
    INDIGO(5, "靛蓝色", "1485ee"),

    /** 紫色 */
    PURPLE(6, "紫色", "6467f0"),

    /** 橘红色 */
    RED_ORANGE(7, "橘红色", "ff6146"),

    /** 浅绿色 */
    LIGHT_GREEN(8, "浅绿色", "95ec69"),

    /** 灰色 */
    GREY(9, "灰色", "b1b2b3"),

    /** 黑色 */
    BLACK(10, "黑色", "1a1b1c");

    @EnumValue
    private final int code;

    /** 名称 */
    private final String name;

    /** 颜色值 */
    private final String value;

    Color(int code, String name, String value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }
}
