package com.inlym.lifehelper.common.base.aliyun.ots.core.utils;

import cn.hutool.core.util.IdUtil;
import com.alicloud.openservices.tablestore.model.*;
import com.google.common.base.CaseFormat;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.AttributeField;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.PrimaryKeyField;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.PrimaryKeyMode;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.Table;
import lombok.SneakyThrows;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 表格存储宽列模型帮助类
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/20
 * @since 1.4.0
 **/
public abstract class WideColumnUtils {
    /**
     * 获取哈希化的字符串主键 ID
     *
     * <h2>说明
     * <p>将分区键进行散列化，让数据分布更均匀，避免热点。目前主要用于转化用户 ID。
     *
     * @param id 整型主键 ID
     *
     * @see <a href="https://help.aliyun.com/document_detail/142533.html">表设计</a>
     */
    private static String getHashedId(int id) {
        String sid = String.valueOf(id);
        String prefix = DigestUtils
            .md5DigestAsHex(sid.getBytes(StandardCharsets.UTF_8))
            .substring(0, 8);

        return prefix + "_" + sid;
    }

    /**
     * 解析哈希化的字符串主键 ID，将其还原为原来的整型 ID
     *
     * @param hashedId 哈希化的字符串主键 ID
     *
     * @since 1.4.0
     */
    private static int parseHashedId(String hashedId) {
        String[] strings = hashedId.split("_");

        String sid = strings[1];
        return Integer.parseInt(sid);
    }

    /**
     * 获取实体对象在表格存储中使用的表名
     *
     * <h2>说明
     * <p>默认使用类名的下划线命名作为表名，可使用 {@link Table} 注解的 {@link Table#name()} 属性覆盖默认值。
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    public static String getTableName(Object entity) {
        Table table = entity
            .getClass()
            .getAnnotation(Table.class);

        // 如果使用了 {@link Table} 注解并指定了表名，则取指定的表名
        if (table != null && StringUtils.hasText(table.name())) {
            return table.name();
        }

        // 否则直接使用“类名”的下划线命名作为“表名”
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entity
            .getClass()
            .getSimpleName());
    }

    /**
     * 获取实体的主键列字段列表（已按照排序字段升序排列）
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    public static List<Field> getPrimaryKeyFieldList(Object entity) {
        return Arrays
            .stream(entity
                .getClass()
                .getDeclaredFields())
            .filter(o -> o.getAnnotation(PrimaryKeyField.class) != null)
            .sorted(Comparator.comparingInt(o -> o
                .getAnnotation(PrimaryKeyField.class)
                .order()))
            .toList();
    }

    /**
     * 获取实体的属性列（非主键列）字段列表
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    public static List<Field> getAttributeFieldList(Object entity) {
        return Arrays
            .stream(entity
                .getClass()
                .getDeclaredFields())
            .filter(o -> o.getAnnotation(PrimaryKeyField.class) == null)
            .toList();
    }

    /**
     * 获取实体对象在表格存储中使用的列名（已兼容主键列和属性列）
     *
     * @param field 字段
     *
     * @since 1.4.0
     */
    public static String getColumnName(Field field) {
        // 主键列注解
        PrimaryKeyField primaryKeyField = field.getAnnotation(PrimaryKeyField.class);
        if (primaryKeyField != null && StringUtils.hasText(primaryKeyField.name())) {
            return primaryKeyField.name();
        }

        // 属性列注解
        AttributeField attributeField = field.getAnnotation(AttributeField.class);
        if (attributeField != null && StringUtils.hasText(attributeField.name())) {
            return attributeField.name();
        }

        // 否则直接使用字段名的下划线命名作为列名
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
    }

    /**
     * 获取字段值转化而成的列值
     *
     * @param field  字段
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    @SneakyThrows
    public static ColumnValue getColumnValue(Field field, Object entity) {
        field.setAccessible(true);
        Object value = field.get(entity);
        return convertToColumnValue(value);
    }

    /**
     * 将普通类型的字段值转化为宽表模型中使用的列值类型
     *
     * @param obj 字段值
     *
     * @since 1.4.0
     */
    public static ColumnValue convertToColumnValue(Object obj) {
        if (obj == null) {
            return ColumnValue.INTERNAL_NULL_VALUE;
        } else if (obj instanceof String) {
            return ColumnValue.fromString((String) obj);
        } else if (obj instanceof Long) {
            return ColumnValue.fromLong((Long) obj);
        } else if (obj instanceof Double) {
            return ColumnValue.fromDouble((Double) obj);
        } else if (obj instanceof Boolean) {
            return ColumnValue.fromBoolean((Boolean) obj);
        } else if (obj instanceof byte[]) {
            return ColumnValue.fromBinary((byte[]) obj);
        } else {
            // 一般实体的数据类型不会弄错，保底错误这里抛出参数错误
            throw new IllegalArgumentException("宽表模型列赋值错误：未支持的数据类型");
        }
    }

    /**
     * 将字段值转化为主键列值
     *
     * @param obj    字段值
     * @param hashed 是否哈希化
     *
     * @since 1.4.0
     */
    public static PrimaryKeyValue getPrimaryKeyValue(Object obj, boolean hashed) {
        if (hashed) {
            obj = getHashedId((int) obj);
        }

        return PrimaryKeyValue.fromColumn(convertToColumnValue(obj));
    }

    /**
     * 构建主键
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    @SneakyThrows
    public static PrimaryKey buildPrimaryKey(Object entity) {
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();

        for (Field field : getPrimaryKeyFieldList(entity)) {
            PrimaryKeyField primaryKeyField = field.getAnnotation(PrimaryKeyField.class);
            boolean hashed = primaryKeyField.hashed();
            String name = getColumnName(field);
            field.setAccessible(true);
            Object fieldValue = field.get(entity);

            if (fieldValue != null) {
                PrimaryKeyValue value = getPrimaryKeyValue(fieldValue, hashed);
                primaryKeyBuilder.addPrimaryKeyColumn(name, value);
            } else {
                throw new IllegalArgumentException("主键字段不允许为空");
            }
        }

        return primaryKeyBuilder.build();
    }

    /**
     * 构建最小主键
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    @SneakyThrows
    public static PrimaryKey buildMinPrimaryKey(Object entity) {
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();

        for (Field field : getPrimaryKeyFieldList(entity)) {
            PrimaryKeyField primaryKeyField = field.getAnnotation(PrimaryKeyField.class);
            boolean hashed = primaryKeyField.hashed();
            String name = getColumnName(field);
            field.setAccessible(true);
            Object fieldValue = field.get(entity);
            if (fieldValue != null) {
                PrimaryKeyValue value = getPrimaryKeyValue(fieldValue, hashed);
                primaryKeyBuilder.addPrimaryKeyColumn(name, value);
            } else {
                primaryKeyBuilder.addPrimaryKeyColumn(name, PrimaryKeyValue.INF_MIN);
            }
        }

        return primaryKeyBuilder.build();
    }

    /**
     * 构建最大主键
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    @SneakyThrows
    public static PrimaryKey buildMaxPrimaryKey(Object entity) {
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();

        for (Field field : getPrimaryKeyFieldList(entity)) {
            PrimaryKeyField primaryKeyField = field.getAnnotation(PrimaryKeyField.class);
            boolean hashed = primaryKeyField.hashed();
            String name = getColumnName(field);
            field.setAccessible(true);
            Object fieldValue = field.get(entity);
            if (fieldValue != null) {
                PrimaryKeyValue value = getPrimaryKeyValue(fieldValue, hashed);
                primaryKeyBuilder.addPrimaryKeyColumn(name, value);
            } else {
                primaryKeyBuilder.addPrimaryKeyColumn(name, PrimaryKeyValue.INF_MAX);
            }
        }

        return primaryKeyBuilder.build();
    }

    /**
     * 将从表格存储查询获取的行构建实体对象
     *
     * @param row   从表格存储查询获取的行
     * @param clazz 对象实体类型
     * @param <T>   对象实体类型
     *
     * @since 1.4.0
     */
    @SneakyThrows
    public static <T> T buildEntity(Row row, Class<T> clazz) {
        // 新建一个实例对象
        T entity = clazz
            .getDeclaredConstructor()
            .newInstance();

        // 遍历实例对象的字段，如果该字段对应的列有值，则给字段赋值
        for (Field field : entity
            .getClass()
            .getDeclaredFields()) {
            String columnName = getColumnName(field);

            PrimaryKeyColumn primaryKeyColumn = row
                .getPrimaryKey()
                .getPrimaryKeyColumn(columnName);
            if (primaryKeyColumn != null) {
                field.setAccessible(true);
                if (field.getType() == String.class) {
                    field.set(entity, primaryKeyColumn
                        .getValue()
                        .asString());
                } else if (field.getType() == Long.class) {
                    PrimaryKeyField primaryKeyField = field.getAnnotation(PrimaryKeyField.class);
                    if (primaryKeyField != null && primaryKeyField.hashed()) {
                        int id = parseHashedId(primaryKeyColumn
                            .getValue()
                            .asString());
                        field.set(entity, (long) id);
                    } else {
                        field.set(entity, primaryKeyColumn
                            .getValue()
                            .asLong());
                    }
                } else if (field.getType() == Integer.class) {
                    PrimaryKeyField primaryKeyField = field.getAnnotation(PrimaryKeyField.class);
                    if (primaryKeyField != null && primaryKeyField.hashed()) {
                        int id = parseHashedId(primaryKeyColumn
                            .getValue()
                            .asString());
                        field.set(entity, id);
                    } else {
                        field.set(entity, (int) primaryKeyColumn
                            .getValue()
                            .asLong());
                    }
                }

                // 备注：能进入这个条件语句，说明该字段是主键字段，就没必要去属性字段再跑一遍了
                continue;
            }

            Column column = row.getLatestColumn(columnName);
            if (column != null) {
                field.setAccessible(true);
                if (field.getType() == String.class) {
                    field.set(entity, column
                        .getValue()
                        .asString());
                } else if (field.getType() == Long.class) {
                    field.set(entity, column
                        .getValue()
                        .asLong());
                } else if (field.getType() == Double.class) {
                    field.set(entity, column
                        .getValue()
                        .asDouble());
                } else if (field.getType() == Boolean.class) {
                    field.set(entity, column
                        .getValue()
                        .asBoolean());
                }
            }
        }

        return entity;
    }

    /**
     * 创建时自动填充一些实体对象字段
     *
     * <h2>什么情况下字段会被自动赋值？
     * <li>（1）标注了 {@link PrimaryKeyField} 注解且 {@link PrimaryKeyField#mode()} 为 {@code PrimaryKeyMode.SIMPLE_UUID}
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    @SneakyThrows
    public static void fillEmptyFieldWhenCreate(Object entity) {
        for (Field field : entity
            .getClass()
            .getDeclaredFields()) {
            PrimaryKeyField primaryKeyField = field.getAnnotation(PrimaryKeyField.class);

            if (primaryKeyField != null && primaryKeyField.mode() == PrimaryKeyMode.SIMPLE_UUID) {
                field.setAccessible(true);
                if (field.get(entity) == null) {
                    // 字段值为空
                    String id = IdUtil.simpleUUID();
                    field.set(entity, id);
                }
            }
        }
    }
}
