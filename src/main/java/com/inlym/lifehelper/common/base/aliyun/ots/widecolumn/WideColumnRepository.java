package com.inlym.lifehelper.common.base.aliyun.ots.widecolumn;

import cn.hutool.core.util.IdUtil;
import com.alicloud.openservices.tablestore.model.ColumnValue;
import com.alicloud.openservices.tablestore.model.PrimaryKey;
import com.google.common.base.CaseFormat;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.AttributeColumn;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.PrimaryKeyColumn;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.PrimaryKeyMode;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.Table;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 表格存储宽表模型存储库
 *
 * <h2>主要用途
 * <p>封装增删改查操作。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/18
 * @since 1.4.0
 **/
@Service
@RequiredArgsConstructor
public class WideColumnRepository<T> {
    private final WideColumnClient client;

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
    private String getTableName(T entity) {
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
     * 获取主键列字段在表格存储中使用的列名
     *
     * @param field 反射获取的字段
     *
     * @since 1.4.0
     */
    private String getPrimaryKeyColumnName(Field field) {
        PrimaryKeyColumn primaryKeyColumn = field.getAnnotation(PrimaryKeyColumn.class);

        // 如果使用的该注解且列名（`name`）属性有值，则直接使用该值作为列名
        if (primaryKeyColumn != null && StringUtils.hasLength(primaryKeyColumn.name())) {
            return primaryKeyColumn.name();
        }

        // 否则直接使用字段名的下划线命名作为列名
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
    }

    /**
     * 获取属性列字段在表格存储中使用的列名
     *
     * @param field 反射获取的字段
     *
     * @since 1.4.0
     */
    private String getAttributeColumnName(Field field) {
        AttributeColumn attributeColumn = field.getAnnotation(AttributeColumn.class);

        // 如果使用的该注解且列名（`name`）属性有值，则直接使用该值作为列名
        if (attributeColumn != null && StringUtils.hasLength(attributeColumn.name())) {
            return attributeColumn.name();
        }

        // 否则直接使用字段名的下划线命名作为列名
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
    }

    /**
     * 从字段获取在表格存储中的列值
     *
     * @param field 反射获取的字段
     *
     * @since 1.4.0
     */
    @SneakyThrows
    private ColumnValue getColumnValue(Field field, T entity) {
        Class<?> type = field.getType();
        Object o = field.get(entity);

        if (o == null) {
            return ColumnValue.INTERNAL_NULL_VALUE;
        }

        // 字段是字符串类型
        if (type == String.class) {
            return ColumnValue.fromString((String) o);
        }

        // 字段是长整型
        if (type == Long.class) {
            return ColumnValue.fromLong((Long) o);
        }

        // 字段是双精度型
        if (type == Double.class) {
            return ColumnValue.fromDouble((Double) o);
        }

        // 字段是布尔型
        if (type == Boolean.class) {
            return ColumnValue.fromBoolean((boolean) o);
        }

        // 备注：{@code ColumnValue.fromBinary} 不会使用，因此不在这里处理了。

        // 一般实体的数据类型不会弄错，保底错误这里抛出参数错误
        throw new IllegalArgumentException("不支持的实体字段类型");
    }

    /**
     * 获取实体的主键列字段列表
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    private List<Field> getPrimaryKeyFieldList(T entity) {
        // 主键列字段的列表
        List<Field> fields = new ArrayList<>();

        // 遍历实体对象的所有字段，将有主键列注解的字段添加到该列表中
        for (Field field : entity
            .getClass()
            .getDeclaredFields()) {
            if (field.getAnnotation(PrimaryKeyColumn.class) != null) {
                fields.add(field);
            }
        }

        // 对列表重排序，按照 `order` 字段升序排列
        fields.sort(Comparator.comparingInt(o -> o
            .getAnnotation(PrimaryKeyColumn.class)
            .order()));

        return fields;
    }

    /**
     * 获取实体的属性列（非主键列）字段列表
     *
     * @param entity 实体
     *
     * @since 1.4.0
     */
    private List<Field> getAttributeFieldList(T entity) {
        List<Field> fields = new ArrayList<>();

        for (Field field : entity
            .getClass()
            .getDeclaredFields()) {
            if (field.getAnnotation(PrimaryKeyColumn.class) == null) {
                fields.add(field);
            }
        }

        return fields;
    }

    /**
     * 构建主键
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    private PrimaryKey buildPrimaryKey(T entity) {

        return null;
    }

    /**
     * 在操作前，对实体对象进行预处理
     *
     * @param entity    实体对象
     * @param operation 操作类型
     *
     * @since 1.4.0
     */
    @SneakyThrows
    private void preprocess(T entity, DataOperation operation) {
        // 对每一个字段都走一遍流程
        for (Field field : entity
            .getClass()
            .getDeclaredFields()) {
            if (field.getAnnotation(PrimaryKeyColumn.class) != null) {
                // 主键列处理
                PrimaryKeyColumn primaryKeyColumn = field.getAnnotation(PrimaryKeyColumn.class);
                if (field.get(entity) == null) {
                    // 字段值为空
                    if (operation == DataOperation.CREATE && primaryKeyColumn.mode() == PrimaryKeyMode.SIMPLE_UUID) {
                        String id = IdUtil.simpleUUID();
                        field.setAccessible(true);
                        field.set(entity, id);
                    }

                    // 除了上述情况自动添加主键值，其余情况均报错
                    throw new NullPointerException("主键列字段值不允许为空！");
                }
            }
        }
    }

    /** 数据操作 */
    private static enum DataOperation {
        /** 增加 */
        CREATE,

        /** 检索 */
        RETRIEVE,

        /** 更新 */
        UPDATE,

        /** 删除 */
        DELETE;

        private DataOperation() {}
    }
}
