package com.inlym.lifehelper.common.base.aliyun.ots.widecolumn;

import cn.hutool.core.util.IdUtil;
import com.alicloud.openservices.tablestore.model.*;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.PrimaryKeyField;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.PrimaryKeyMode;
import com.inlym.lifehelper.common.base.aliyun.ots.core.utils.WideColumnUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
    /** 表示逻辑删除的列的名称，该字段自动化管理 */
    private static final String DELETED_COLUMN_NAME = "_deleted";

    private final WideColumnClient client;

    /**
     * 将从表格存储查询获取的行转化为实体对象
     *
     * @param row   从表格存储查询获取的行
     * @param clazz 对象实体类型
     * @param <T>   对象实体类型
     *
     * @since 1.4.0
     */
    @SneakyThrows
    public static <T> T buildEntity(Row row, Class<T> clazz) {
        return null;
    }

    /**
     * 将表格存储的列值赋值到实体对象的字段上
     *
     * @param column 表格存储的列
     * @param field  实体对象的字段
     *
     * @since 1.4.0
     */
    private static void setColumnToField(Column column, Field field) {

    }

    /**
     * 对非必填主键列字段自动赋值
     *
     * <h2>什么情况下字段会被自动赋值？
     * <li>只有标注了 {@link PrimaryKeyField} 注解且 {@link PrimaryKeyField#mode()} 为 {@code PrimaryKeyMode.SIMPLE_UUID}
     * <li>只有在“创建”操作时才会使用。
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    @SneakyThrows
    private void fillEmptyField(T entity) {
        for (Field field : entity
            .getClass()
            .getDeclaredFields()) {
            if (field.getAnnotation(PrimaryKeyField.class) != null) {
                PrimaryKeyField primaryKeyField = field.getAnnotation(PrimaryKeyField.class);
                field.setAccessible(true);
                if (field.get(entity) == null) {
                    // 字段值为空
                    if (primaryKeyField.mode() == PrimaryKeyMode.SIMPLE_UUID) {
                        String id = IdUtil.simpleUUID();
                        field.setAccessible(true);
                        field.set(entity, id);
                    }
                }
            }
        }
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

        return WideColumnUtils.convert(o);
    }

    /**
     * 获取实体的属性列（非主键列）字段列表
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    private List<Field> getAttributeFieldList(T entity) {
        List<Field> fields = new ArrayList<>();

        for (Field field : entity
            .getClass()
            .getDeclaredFields()) {
            if (field.getAnnotation(PrimaryKeyField.class) == null) {
                fields.add(field);
            }
        }

        return fields;
    }

    /**
     * 创建实体
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    @SneakyThrows
    public T create(T entity) {
        fillEmptyField(entity);
        RowPutChange change = new RowPutChange(WideColumnUtils.getTableName(entity), WideColumnUtils.buildPrimaryKey(entity));
        for (Field field : getAttributeFieldList(entity)) {
            String name = WideColumnUtils.getAttributeColumnName(field);
            ColumnValue value = getColumnValue(field, entity);
            if (!ColumnValue.INTERNAL_NULL_VALUE.equals(value)) {
                change.addColumn(name, value);
            }
        }

        client.putRow(new PutRowRequest(change));
        return entity;
    }

    /**
     * 删除实体
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    public void delete(T entity) {
        RowDeleteChange change = new RowDeleteChange(WideColumnUtils.getTableName(entity), WideColumnUtils.buildPrimaryKey(entity));
        client.deleteRow(new DeleteRowRequest(change));
    }

    /**
     * 更新实体
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    @SneakyThrows
    public T update(T entity) {
        RowUpdateChange change = new RowUpdateChange(WideColumnUtils.getTableName(entity), WideColumnUtils.buildPrimaryKey(entity));
        for (Field field : getAttributeFieldList(entity)) {
            field.setAccessible(true);
            if (field.get(entity) != null) {
                String name = WideColumnUtils.getAttributeColumnName(field);
                ColumnValue value = getColumnValue(field, entity);
                change.put(name, value);
            }
        }

        client.updateRow(new UpdateRowRequest(change));
        return entity;
    }

    /**
     * 根据主键列找到一行记录
     *
     * @param entity （包含主键列字段的）实体对象
     *
     * @since 1.4.0
     */
    public T findOne(T entity) {
        SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(WideColumnUtils.getTableName(entity), WideColumnUtils.buildPrimaryKey(entity));
        criteria.setMaxVersions(1);

        return null;
    }
}
