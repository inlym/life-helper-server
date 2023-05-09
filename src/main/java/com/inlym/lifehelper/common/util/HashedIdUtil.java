package com.inlym.lifehelper.common.util;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * 哈希化 ID 辅助方法
 *
 * <h2>主要用途
 * <p>封装内部可直接调用的哈希化 ID 辅助方法，目前用于为表格存储生成分区键。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/5/6
 * @since 2.0.0
 **/
public abstract class HashedIdUtil {
    /**
     * 获取哈希化的字符串主键 ID
     *
     * <h2>说明
     * <p>目前用于表格存储分区键，将分区键进行散列化，让数据分布更均匀，避免热点。目前主要用于转化用户 ID。
     *
     * @param id 整型主键 ID
     *
     * @see <a href="https://help.aliyun.com/document_detail/142533.html">表设计</a>
     */
    public static String create(int id) {
        String sid = String.valueOf(id);
        String prefix = DigestUtils.md5DigestAsHex(sid.getBytes(StandardCharsets.UTF_8))
                                   .substring(0, 8);

        return prefix + "_" + sid;
    }

    /**
     * 解析哈希化的字符串主键 ID，将其还原为原来的整型 ID
     *
     * <h2>说明
     * <p>目前用于表格存储分区键。
     *
     * @param hashedId 哈希化的字符串主键 ID
     *
     * @since 1.4.0
     */
    public static int parse(String hashedId) {
        String[] strings = hashedId.split("_");

        String sid = strings[1];
        return Integer.parseInt(sid);
    }
}
