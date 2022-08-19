package com.inlym.lifehelper.common.base.aliyun.ots.core.utils;

import com.alicloud.openservices.tablestore.model.ColumnValue;
import com.alicloud.openservices.tablestore.model.PrimaryKey;
import com.alicloud.openservices.tablestore.model.PrimaryKeyBuilder;
import com.alicloud.openservices.tablestore.model.PrimaryKeyValue;
import com.google.common.base.CaseFormat;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.AttributeField;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.PrimaryKeyField;
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
 * <h2>主要用途
 * <p>将一些公共工具类方法从 {@link com.inlym.lifehelper.common.base.aliyun.ots.widecolumn.WideColumnRepository} 冲抽取出来。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/20
 * @since 1.x.x
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
    public static String getHashedId(long id) {
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
    public static long parseHashedId(String hashedId) {
        String[] strings = hashedId.split("_");

        String sid = strings[1];
        return Long.parseLong(sid);
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
        if (table != null && StringUtils.hasLength(table.name())) {
            return table.name();
        }

        // 否则直接使用“类名”的下划线命名作为“表名”
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entity
            .getClass()
            .getSimpleName());
    }

    /**
     * 获取实体对象主键字段在表格存储中使用的列名
     *
     * <h2>备注（2022.08.20）
     * <h2>在前置环节，已经校验了该注解存在，因此在此处使用时，默认该前提。
     *
     * @param field 字段
     *
     * @since 1.4.0
     */
    public static String getPrimaryKeyColumnName(Field field) {
        PrimaryKeyField primaryKeyField = field.getAnnotation(PrimaryKeyField.class);
        if (StringUtils.hasText(primaryKeyField.name())) {
            return primaryKeyField.name();
        } else {
            return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
        }
    }

    /**
     * 获取属性列的“列名”
     *
     * <h2>备注（2022.08.20）
     * <h2>标注属性列字段的 {@link com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.AttributeField} 为选填，注意判断。
     *
     * @param field 字段
     *
     * @since 1.4.0
     */
    public static String getAttributeColumnName(Field field) {
        AttributeField attributeField = field.getAnnotation(AttributeField.class);

        // 如果使用的该注解且列名（`name`）属性有值，则直接使用该值作为列名
        if (attributeField != null && StringUtils.hasText(attributeField.name())) {
            return attributeField.name();
        }

        // 否则直接使用字段名的下划线命名作为列名
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
    }

    /**
     * 将字段值转化为宽表模型中使用的列值类型
     *
     * @param obj 字段值
     *
     * @since 1.4.0
     */
    public static ColumnValue convert(Object obj) {
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
            obj = getHashedId((Long) obj);
        }

        return PrimaryKeyValue.fromColumn(convert(obj));
    }

    /**
     * 将字段值转化为属性列值
     *
     * @param obj 字段值
     *
     * @since 1.4.0
     */
    public static ColumnValue getAttributeColumnValue(Object obj) {
        return convert(obj);
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

        // 主键列字段的列表
        List<Field> fields = Arrays
            .stream(entity
                .getClass()
                .getDeclaredFields())
            .filter(o -> o.getAnnotation(PrimaryKeyField.class) != null)
            .sorted(Comparator.comparingInt(o -> o
                .getAnnotation(PrimaryKeyField.class)
                .order()))
            .toList();

        for (Field field : fields) {
            PrimaryKeyField primaryKeyField = field.getAnnotation(PrimaryKeyField.class);
            boolean hashed = primaryKeyField.hashed();

            String name = getPrimaryKeyColumnName(field);
            PrimaryKeyValue value = getPrimaryKeyValue(field.get(entity), hashed);
            primaryKeyBuilder.addPrimaryKeyColumn(name, value);
        }

        return primaryKeyBuilder.build();
    }
}
