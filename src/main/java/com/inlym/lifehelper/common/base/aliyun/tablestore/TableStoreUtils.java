package com.inlym.lifehelper.common.base.aliyun.tablestore;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 阿里云表格存储辅助方法
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/13
 * @since 1.3.0
 **/
public abstract class TableStoreUtils {
    /**
     * 获取随机主键 ID
     *
     * <h2>说明
     * <p>目前为去掉短横线的 UUID。
     *
     * @since 1.3.0
     */
    public static String getRandomId() {
        return UUID
            .randomUUID()
            .toString()
            .toLowerCase()
            .replaceAll("-", "");
    }

    /**
     * 获取分散的字符串主键 ID
     *
     * <h2>说明
     * <p>将分区键进行散列化，让数据分布更均匀，避免热点。目前主要用于转化用户 ID。
     *
     * @param id 整型主键 ID
     *
     * @see <a href="https://help.aliyun.com/document_detail/142533.html">表设计</a>
     */
    public static String getNonClusteredId(int id) {
        String sid = String.valueOf(id);
        String prefix = DigestUtils
            .md5DigestAsHex(sid.getBytes(StandardCharsets.UTF_8))
            .substring(0, 8);

        return prefix + "_" + sid;
    }
}
