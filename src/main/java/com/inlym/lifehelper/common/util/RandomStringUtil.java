package com.inlym.lifehelper.common.util;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 随机字符串工具集
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/5/31
 * @since 2.3.0
 **/
public abstract class RandomStringUtil {
    /** 空字符串 */
    public static final String EMPTY_STRING = "";

    /**
     * 生成指定长度的随机字符串
     *
     * @param length 字符串长度
     *
     * @return 生成的字符串
     * @date 2024/5/31
     * @since 2.3.0
     */
    public static String generate(int length) {
        // 定义字符集，包括大写字母、小写字母和数字
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();

        return IntStream
                .range(0, length)
                .map(i -> random.nextInt(characters.length()))
                .mapToObj(randomIndex -> String.valueOf(characters.charAt(randomIndex)))
                .collect(Collectors.joining());
    }
}
