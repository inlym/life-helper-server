package com.inlym.lifehelper.common.base.aliyun.ots.core.utils;

import com.alicloud.openservices.tablestore.model.ColumnValue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 表格存储公共辅助方法
 *
 * <h2>说明
 * <p>在各个存储模型中都用到的辅助方法放在此类中，否则放在各个存储模型辅助方法类中。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/21
 * @since 1.7.0
 **/
public abstract class TableStoreUtils {
    /**
     * 将普通类型的字段值转化为宽表模型中使用的列值类型
     *
     * @param obj 字段值
     *
     * @since 1.7.0
     */
    public static ColumnValue convertToColumnValue(Object obj) {
        if (obj == null) {
            return ColumnValue.INTERNAL_NULL_VALUE;
        } else if (obj instanceof String) {
            return ColumnValue.fromString((String) obj);
        } else if (obj instanceof Long) {
            return ColumnValue.fromLong((Long) obj);
        } else if (obj instanceof Integer) {
            return ColumnValue.fromLong(Long.valueOf((Integer) obj));
        } else if (obj instanceof Double) {
            return ColumnValue.fromDouble((Double) obj);
        } else if (obj instanceof Boolean) {
            return ColumnValue.fromBoolean((Boolean) obj);
        } else if (obj instanceof byte[]) {
            return ColumnValue.fromBinary((byte[]) obj);
        } else if (obj instanceof LocalDateTime) {
            return ColumnValue.fromLong(((LocalDateTime) obj)
                .toInstant(ZoneId
                    .systemDefault()
                    .getRules()
                    .getOffset(LocalDateTime.now()))
                .toEpochMilli());
        } else if (obj instanceof LocalDate) {
            return ColumnValue.fromString(obj.toString());
        } else {
            // 一般实体的数据类型不会弄错，保底错误这里抛出参数错误
            throw new IllegalArgumentException("表格存储列赋值错误：未支持的数据类型");
        }
    }
}
