package com.weutil.common.util;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 随机字符串工具集
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @since 3.0.0
 **/
public abstract class RandomStringUtil {
    /**
     * 生成指定长度的随机字符串（包含大小写字母和数字）
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

    /**
     * 生成数字串（全部由数字组成的字符串）
     *
     * @param length 字符串长度
     *
     * @date 2024/6/14
     * @since 2.3.0
     */
    public static String generateNumericString(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}
