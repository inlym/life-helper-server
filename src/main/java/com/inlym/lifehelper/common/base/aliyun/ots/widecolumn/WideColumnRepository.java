package com.inlym.lifehelper.common.base.aliyun.ots.widecolumn;

import com.alicloud.openservices.tablestore.model.*;
import com.alicloud.openservices.tablestore.model.filter.SingleColumnValueFilter;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.PrimaryKeyField;
import com.inlym.lifehelper.common.base.aliyun.ots.core.utils.WideColumnUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
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
     * 从字段获取在表格存储中的列值
     *
     * @param field 反射获取的字段
     *
     * @since 1.4.0
     */
    @SneakyThrows
    private ColumnValue getColumnValue(Field field, T entity) {
        Class<?> type = field.getType();
        field.setAccessible(true);
        Object o = field.get(entity);

        return WideColumnUtils.convertToColumnValue(o);
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
        WideColumnUtils.fillEmptyFieldWhenCreate(entity);
        RowPutChange change = new RowPutChange(WideColumnUtils.getTableName(entity), WideColumnUtils.buildPrimaryKey(entity));
        for (Field field : WideColumnUtils.getAttributeFieldList(entity)) {
            String name = WideColumnUtils.getColumnName(field);
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
        for (Field field : WideColumnUtils.getAttributeFieldList(entity)) {
            field.setAccessible(true);
            if (field.get(entity) != null) {
                String name = WideColumnUtils.getColumnName(field);
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

    /**
     * 根据分区键（即第一个主键）查询所有结果
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public List<T> findAllByPartitionKey(T entity) {
        List<Field> primaryKeyFieldList = WideColumnUtils.getPrimaryKeyFieldList(entity);
        PrimaryKeyBuilder startPrimaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        PrimaryKeyBuilder endPrimaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();

        for (int i = 0; i < primaryKeyFieldList.size(); i++) {
            Field field = primaryKeyFieldList.get(i);
            String name = WideColumnUtils.getColumnName(field);

            if (i == 0) {
                PrimaryKeyField primaryKeyField = field.getAnnotation(PrimaryKeyField.class);
                PrimaryKeyValue value = WideColumnUtils.getPrimaryKeyValue(field.get(entity), primaryKeyField.hashed());
                startPrimaryKeyBuilder.addPrimaryKeyColumn(name, value);
                endPrimaryKeyBuilder.addPrimaryKeyColumn(name, value);
            } else {
                startPrimaryKeyBuilder.addPrimaryKeyColumn(name, PrimaryKeyValue.INF_MIN);
                endPrimaryKeyBuilder.addPrimaryKeyColumn(name, PrimaryKeyValue.INF_MAX);
            }
        }

        // 兼容“软删除（逻辑删除）”功能，自定义一个过滤器
        SingleColumnValueFilter filter = new SingleColumnValueFilter(DELETED_COLUMN_NAME, SingleColumnValueFilter.CompareOperator.EQUAL, ColumnValue.fromBoolean(false));
        filter.setLatestVersionsOnly(true);
        filter.setPassIfMissing(true);

        RangeRowQueryCriteria criteria = new RangeRowQueryCriteria(WideColumnUtils.getTableName(entity));
        criteria.setInclusiveStartPrimaryKey(startPrimaryKeyBuilder.build());
        criteria.setExclusiveEndPrimaryKey(endPrimaryKeyBuilder.build());
        criteria.setMaxVersions(1);
        criteria.setFilter(filter);

        List<T> list = new ArrayList<>();
        while (true) {
            GetRangeResponse response = client.getRange(new GetRangeRequest(criteria));
            for (Row row : response.getRows()) {
                Class<T> tClass = (Class<T>) ((ParameterizedType) this
                    .getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
                list.add(WideColumnUtils.buildEntity(row, tClass));
            }

            if (response.getNextStartPrimaryKey() != null) {
                criteria.setInclusiveStartPrimaryKey(response.getNextStartPrimaryKey());
            } else {
                break;
            }
        }

        return list;
    }
}
