package com.inlym.lifehelper.common.base.aliyun.ots.widecolumn;

import com.alicloud.openservices.tablestore.model.*;
import com.inlym.lifehelper.common.base.aliyun.ots.core.utils.WideColumnUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
     * 从字段获取在表格存储中的列值
     *
     * @param field 反射获取的字段
     *
     * @since 1.4.0
     */
    @SneakyThrows
    private ColumnValue getColumnValue(Field field, T entity) {
        field.setAccessible(true);
        Object o = field.get(entity);

        return WideColumnUtils.convertToColumnValue(o);
    }

    /**
     * 将表格存储中的行转换为实体对象
     *
     * @param row 表格存储中的行
     *
     * @since 1.4.0
     */
    private T rowToEntity(Row row) {
        Class<T> tClass;

        Type type = this
            .getClass()
            .getGenericSuperclass();

        if (type instanceof ParameterizedType pt) {
            Type[] types = pt.getActualTypeArguments();
            tClass = (Class<T>) types[0];
        } else {
            throw new IllegalArgumentException("类型错误");
        }

        return WideColumnUtils.buildEntity(row, tClass);
    }

    /**
     * 创建实体
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    public T create(T entity) {
        WideColumnUtils.fillEmptyFieldWhenCreate(entity);
        RowPutChange change = new RowPutChange(WideColumnUtils.getTableName(entity), WideColumnUtils.buildPrimaryKey(entity));
        for (Field field : WideColumnUtils.getAttributeFieldList(entity)) {
            String name = WideColumnUtils.getColumnName(field);
            ColumnValue value = getColumnValue(field, entity);
            if (!ColumnValue.INTERNAL_NULL_VALUE.equals(value)) {
                // 仅添加非空列
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
        for (Field field : WideColumnUtils.getAttributeFieldList(entity)) {
            ColumnValue value = getColumnValue(field, entity);
            if (!ColumnValue.INTERNAL_NULL_VALUE.equals(value)) {
                String name = WideColumnUtils.getColumnName(field);
                change.put(name, value);
            }
        }

        client.updateRow(new UpdateRowRequest(change));

        return entity;
    }
}
