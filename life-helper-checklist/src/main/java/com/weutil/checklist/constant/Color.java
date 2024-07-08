package com.weutil.checklist.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 颜色
 *
 * <h2>说明
 * <p>颜色的“色号”由前端维护，此处仅维护枚举值。
 *
 * <h2>色号
 * <p>0,绿色,06ae56
 * <p>1,红色,fa5151
 * <p>2,橙色,fa9d3b
 * <p>3,黄色,ffc300
 * <p>4,蓝色,10aeff
 * <p>5,靛蓝色,1485ee
 * <p>6,紫色,6467f0
 * <p>7,橘红色,ff6146
 * <p>8,浅绿色,95ec69
 * <p>9,灰色,b1b2b3
 * <p>10,黑色,1a1b1c
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/2
 * @since 2.3.0
 */
@Getter
public enum Color {
    /** 绿色（默认值） */
    GREEN(0),

    /** 红色 */
    RED(1),

    /** 橘黄色 */
    ORANGE(2),

    /** 黄色 */
    YELLOW(3),

    /** 蓝色 */
    BLUE(4),

    /** 靛蓝色 */
    INDIGO(5),

    /** 紫色 */
    PURPLE(6),

    /** 橘红色 */
    RED_ORANGE(7),

    /** 浅绿色 */
    LIGHT_GREEN(8),

    /** 灰色 */
    GREY(9),

    /** 黑色 */
    BLACK(10);

    @EnumValue
    @JsonValue
    private final int code;

    Color(int code) {
        this.code = code;
    }

    public static Color fromCode(int code) {
        for (Color color : Color.values()) {
            if (color.getCode() == code) {
                return color;
            }
        }
        throw new IllegalArgumentException("无效的 code 值：" + code);
    }
}
