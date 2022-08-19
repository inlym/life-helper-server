package com.inlym.lifehelper.common.base.aliyun.ots;

import com.alicloud.openservices.tablestore.model.*;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.AttributeField;
import lombok.SneakyThrows;
import org.springframework.util.DigestUtils;

import java.lang.reflect.Field;
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
    public static String getHashedId(int id) {
        String sid = String.valueOf(id);
        String prefix = DigestUtils
            .md5DigestAsHex(sid.getBytes(StandardCharsets.UTF_8))
            .substring(0, 8);

        return prefix + "_" + sid;
    }

    /**
     * 解析哈希化的字符串主键 ID
     *
     * @param hashedId 哈希化的字符串主键 ID
     *
     * @since 1.4.0
     */
    public static int parseHashedId(String hashedId) {
        String[] strings = hashedId.split("_");

        String sid = strings[1];
        return Integer.parseInt(sid);
    }

    /**
     * 根据表格存储的行建立一个实体
     *
     * <h2>说明
     * <p>当前版本不考虑性能，先实现，后续再优化。
     *
     * @param row   表格存储的行
     * @param clazz 对象类
     *
     * @since 1.3.0
     */
    @SneakyThrows
    public static <T> T buildEntity(Row row, Class<T> clazz) {
        T obj = clazz
            .getDeclaredConstructor()
            .newInstance();

        for (PrimaryKeyColumn column : row
            .getPrimaryKey()
            .getPrimaryKeyColumns()) {
            PrimaryKeyValue primaryKeyValue = column.getValue();
            for (Field field : clazz.getDeclaredFields()) {
                AttributeField attributeField = field.getDeclaredAnnotation(AttributeField.class);
                String columnName = attributeField.name();
                if (column
                    .getName()
                    .equals(columnName)) {
                    // 将字段设为可访问
                    field.setAccessible(true);

                    // 根据实体类型对字段赋值
                    if (field.getType() == String.class) {
                        field.set(obj, primaryKeyValue.asString());
                    } else if (field.getType() == Long.class) {
                        field.set(obj, primaryKeyValue.asLong());
                    } else {
                        // 一般不会走到这里
                        throw new RuntimeException("TableStore Build Entity Error!");
                    }
                }
            }
        }

        for (Column column : row.getColumns()) {
            ColumnValue columnValue = column.getValue();
            for (Field field : clazz.getDeclaredFields()) {
                String columnName = field
                    .getDeclaredAnnotation(AttributeField.class)
                    .name();
                if (column
                    .getName()
                    .equals(columnName)) {
                    field.setAccessible(true);
                    if (field.getType() == String.class) {
                        field.set(obj, columnValue.asString());
                    } else if (field.getType() == Long.class) {
                        field.set(obj, columnValue.asLong());
                    } else if (field.getType() == Boolean.class) {
                        field.set(obj, columnValue.asBoolean());
                    } else if (field.getType() == Double.class) {
                        field.set(obj, columnValue.asDouble());
                    } else {
                        throw new RuntimeException("TableStore Build Entity Error!");
                    }
                }
            }
        }

        return obj;
    }
}
