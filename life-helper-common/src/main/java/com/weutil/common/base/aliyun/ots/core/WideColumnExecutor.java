package com.weutil.common.base.aliyun.ots.core;

import com.alicloud.openservices.tablestore.model.*;
import com.alicloud.openservices.tablestore.model.filter.SingleColumnValueFilter;
import com.weutil.common.base.aliyun.ots.core.model.WideColumnClient;
import com.weutil.common.base.aliyun.ots.core.utils.WideColumnUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 表格存储-宽表模型-执行器
 *
 * <h2>主要用途
 * <p>用于执行对宽表模型的增删改查操作。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/27
 * @since 1.4.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class WideColumnExecutor {
    /**
     * 表示逻辑删除的列的名称，该字段自动化管理
     */
    private static final String DELETED_COLUMN_NAME = "_deleted";

    private final WideColumnClient client;

    /**
     * 新增一行数据
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     *
     * @see <a href="https://help.aliyun.com/document_detail/43013.htm">写入数据</a>
     * @since 1.4.0
     */
    public <T> T create(T entity) {
        WideColumnUtils.fillEmptyFieldWhenCreate(entity);

        String tableName = WideColumnUtils.getTableName(entity);
        PrimaryKey primaryKey = WideColumnUtils.buildPrimaryKey(entity);

        RowPutChange change = new RowPutChange(tableName, primaryKey);
        for (Field field : WideColumnUtils.getAttributeFieldList(entity)) {
            // 静态成员变量不参与该流程
            if (Modifier
                    .toString(field.getModifiers())
                    .contains("static")) {
                continue;
            }

            String name = WideColumnUtils.getColumnName(field);
            ColumnValue value = WideColumnUtils.getColumnValue(field, entity);
            if (!ColumnValue.INTERNAL_NULL_VALUE.equals(value)) {
                // 仅添加非空列
                change.addColumn(name, value);
            }
        }

        client.putRow(new PutRowRequest(change));

        return entity;
    }

    /**
     * 删除一行数据
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    public <T> void delete(T entity) {
        String tableName = WideColumnUtils.getTableName(entity);
        PrimaryKey primaryKey = WideColumnUtils.buildPrimaryKey(entity);

        RowDeleteChange change = new RowDeleteChange(tableName, primaryKey);
        client.deleteRow(new DeleteRowRequest(change));
    }

    /**
     * 更新一行数据
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     *
     * @see <a href="https://help.aliyun.com/document_detail/43013.html">写入数据</a>
     * @since 1.4.0
     */
    public <T> T update(T entity) {
        String tableName = WideColumnUtils.getTableName(entity);
        PrimaryKey primaryKey = WideColumnUtils.buildPrimaryKey(entity);

        RowUpdateChange change = new RowUpdateChange(tableName, primaryKey);
        Condition condition = new Condition(RowExistenceExpectation.IGNORE);
        change.setCondition(condition);

        for (Field field : WideColumnUtils.getAttributeFieldList(entity)) {
            // 静态成员变量不参与该流程
            if (Modifier
                    .toString(field.getModifiers())
                    .contains("static")) {
                continue;
            }

            ColumnValue columnValue = WideColumnUtils.getColumnValue(field, entity);
            if (!ColumnValue.INTERNAL_NULL_VALUE.equals(columnValue)) {
                String name = WideColumnUtils.getColumnName(field);
                change.put(name, columnValue);
            }
        }

        client.updateRow(new UpdateRowRequest(change));

        return entity;
    }

    /**
     * 通过实体对象中的主键进行查找，如果不存在则返回 null
     *
     * <h2>备注（2022.08.27）：
     * <p>{@code Class<T> clazz} 这个参数，理论是可以直接去掉，但尝试了很多办法均不是很有效，因此等待后期优化。
     *
     * @param entity 实体对象
     * @param clazz  实体类
     * @param <T>    实体类型
     *
     * @since 1.4.0
     */
    public <T> T findOne(T entity, Class<T> clazz) {
        String tableName = WideColumnUtils.getTableName(entity);
        PrimaryKey primaryKey = WideColumnUtils.buildPrimaryKey(entity);
        SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(tableName, primaryKey);
        criteria.setMaxVersions(1);

        GetRowResponse response = client.getRow(new GetRowRequest(criteria));
        Row row = response.getRow();
        if (row != null) {
            return WideColumnUtils.buildEntity(response.getRow(), clazz);
        } else {
            return null;
        }
    }

    /**
     * 通过实体对象中的主键进行范围查找
     *
     * @param entity 实体对象
     * @param clazz  实体类
     * @param <T>    实体类型
     *
     * @see <a href="https://help.aliyun.com/document_detail/43017.html">读取数据</a>
     * @since 1.1.0
     */
    public <T> List<T> findAll(T entity, Class<T> clazz) {
        String tableName = WideColumnUtils.getTableName(entity);

        // 兼容“软删除（逻辑删除）”功能，自定义一个过滤器
        SingleColumnValueFilter filter = new SingleColumnValueFilter(DELETED_COLUMN_NAME, SingleColumnValueFilter.CompareOperator.EQUAL,
                ColumnValue.fromBoolean(false));
        filter.setLatestVersionsOnly(true);
        filter.setPassIfMissing(true);

        RangeRowQueryCriteria criteria = new RangeRowQueryCriteria(tableName);
        criteria.setInclusiveStartPrimaryKey(WideColumnUtils.buildMinPrimaryKey(entity));
        criteria.setExclusiveEndPrimaryKey(WideColumnUtils.buildMaxPrimaryKey(entity));
        criteria.setMaxVersions(1);
        criteria.setFilter(filter);

        List<T> list = new ArrayList<>();
        while (true) {
            GetRangeResponse response = client.getRange(new GetRangeRequest(criteria));
            for (Row row : response.getRows()) {
                if (row != null) {
                    list.add(WideColumnUtils.buildEntity(row, clazz));
                }
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
