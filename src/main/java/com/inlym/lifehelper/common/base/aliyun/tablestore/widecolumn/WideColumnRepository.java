package com.inlym.lifehelper.common.base.aliyun.tablestore.widecolumn;

import com.alicloud.openservices.tablestore.model.*;
import com.google.common.base.CaseFormat;
import com.inlym.lifehelper.common.base.aliyun.tablestore.annotation.Column;
import com.inlym.lifehelper.common.base.aliyun.tablestore.annotation.PrimaryKeyColumn;
import com.inlym.lifehelper.common.base.aliyun.tablestore.annotation.Table;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 宽表模型存储库
 *
 * <h2>说明
 * <p>继承这个父类，然后直接调用增删改查方法。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/27
 * @since 1.3.0
 **/
@Service
@RequiredArgsConstructor
public class WideColumnRepository<T> {
    private final WideColumnClient client;

    /**
     * 获取在表格存储中使用的表名
     *
     * <h2>说明
     * <p>默认使用类名的下划线模式作为表名，可使用 {@link Table} 注解覆盖默认值。
     *
     * <h2>注意事项（2022.07.02）
     * <p>临时关闭了检查，即代码中的第一句有警告，目前还不知道怎么处理。
     *
     * @param entity 实体
     *
     * @since 1.3.0
     */
    @SuppressWarnings("unchecked")
    public String getTableName(T entity) {
        Class<T> clazz = (Class<T>) entity.getClass();
        Table table = clazz.getAnnotation(Table.class);

        // 以注解标记的“表名”优先
        if (table != null) {
            return table.value();
        }

        // 默认使用“类名”的下划线命名方式作为“表名”
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
    }

    /**
     * 获取对象字段在表格存储中使用的列名
     *
     * <h2>说明
     * <p>默认使用类名的下划线模式作为表名，可使用 {@link Column} 注解覆盖默认值。
     *
     * @param field 字段
     *
     * @since 1.3.0
     */
    public String getColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);

        // 以注解标记的“列名”优先
        if (column != null) {
            return column.value();
        }

        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
    }

    /**
     * 将主键字段值转化为表格存储的列值
     *
     * @param field 字段
     *
     * @since 1.3.0
     */
    @SneakyThrows
    public PrimaryKeyValue getPrimaryKeyValue(Field field, T entity) {
        Class<?> type = field.getType();
        Object o = field.get(entity);

        if (type == String.class) {
            return PrimaryKeyValue.fromString((String) o);
        } else if (type == Long.class) {
            return PrimaryKeyValue.fromLong((Long) o);
        } else {
            throw new RuntimeException("表格存储实体字段类型错误");
        }
    }

    /**
     * 将字段值转化为表格存储的列值
     *
     * @param field 字段
     *
     * @since 1.3.0
     */
    @SneakyThrows
    public ColumnValue getColumnValue(Field field, T entity) {
        Class<?> type = field.getType();
        Object o = field.get(entity);

        System.out.println(o);

        if (o == null) {
            return null;
        }

        if (type == String.class) {
            return ColumnValue.fromString((String) o);
        } else if (type == Long.class) {
            return ColumnValue.fromLong((Long) o);
        } else if (type == Double.class) {
            return ColumnValue.fromDouble((Double) o);
        } else if (type == Boolean.class) {
            return ColumnValue.fromBoolean((boolean) o);
        } else {
            throw new RuntimeException("表格存储实体字段类型错误");
        }
    }

    /**
     * 对主键字段进行重排序
     *
     * @param list 主键字段列表
     *
     * @since 1.3.0
     */
    private void sortPrimaryKeyFields(List<Field> list) {
        list.sort(Comparator.comparingInt(o -> o
            .getAnnotation(PrimaryKeyColumn.class)
            .order()));
    }

    public T create(T entity) {
        String tableName = getTableName(entity);

        // 主键字段列表
        List<Field> primaryKeyFields = new ArrayList<>();

        // 属性字段列表
        List<Field> attributeFields = new ArrayList<>();

        for (Field field : entity
            .getClass()
            .getDeclaredFields()) {
            if (field.getAnnotation(PrimaryKeyColumn.class) != null) {
                primaryKeyFields.add(field);
            } else {
                attributeFields.add(field);
            }
        }

        // 主键列是有顺序的，进行重排序
        sortPrimaryKeyFields(primaryKeyFields);

        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        for (Field field : primaryKeyFields) {
            String columnName = getColumnName(field);
            field.setAccessible(true);
            primaryKeyBuilder.addPrimaryKeyColumn(columnName, getPrimaryKeyValue(field, entity));
        }

        PrimaryKey primaryKey = primaryKeyBuilder.build();
        RowPutChange rowPutChange = new RowPutChange(WideColumnTables.ALBUM, primaryKey);
        for (Field field : attributeFields) {
            String columnName = getColumnName(field);
            field.setAccessible(true);
            ColumnValue columnValue = getColumnValue(field, entity);
            if (columnValue != null) {
                rowPutChange.addColumn(columnName, columnValue);
            }
        }

        client.putRow(new PutRowRequest(rowPutChange));

        return entity;
    }
}
