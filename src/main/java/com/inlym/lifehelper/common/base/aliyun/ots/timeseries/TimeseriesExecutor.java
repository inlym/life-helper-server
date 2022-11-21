package com.inlym.lifehelper.common.base.aliyun.ots.timeseries;

import com.alicloud.openservices.tablestore.TimeseriesClient;
import com.alicloud.openservices.tablestore.model.ColumnValue;
import com.alicloud.openservices.tablestore.model.timeseries.PutTimeseriesDataRequest;
import com.alicloud.openservices.tablestore.model.timeseries.PutTimeseriesDataResponse;
import com.alicloud.openservices.tablestore.model.timeseries.TimeseriesKey;
import com.alicloud.openservices.tablestore.model.timeseries.TimeseriesRow;
import com.inlym.lifehelper.common.base.aliyun.ots.core.utils.TimeseriesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 表格存储时序模型执行器
 *
 * <h2>主要用途
 * <p>执行对时序模型数据的增删改查等操作。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/21
 * @since 1.7.0
 **/
@Service
@RequiredArgsConstructor
public class TimeseriesExecutor {
    private final TimeseriesClient client;

    /**
     * 新增一条记录
     *
     * @param entity 实体对象
     *
     * @see <a href="https://help.aliyun.com/document_detail/341805.html">写入时序数据</a>
     * @since 1.7.0
     */
    public <T> boolean insert(T entity) {
        String tableName = TimeseriesUtils.getTableName(entity);
        String measurementName = TimeseriesUtils.getMeasurementName(entity);
        String dataSource = TimeseriesUtils.getDataSource(entity);
        Map<String, String> tags = TimeseriesUtils.buildTags(entity);

        TimeseriesKey timeseriesKey;
        if (StringUtils.hasText(dataSource)) {
            timeseriesKey = new TimeseriesKey(measurementName, dataSource, tags);
        } else {
            timeseriesKey = new TimeseriesKey(measurementName, tags);
        }

        TimeseriesRow row = new TimeseriesRow(timeseriesKey, System.currentTimeMillis() * 1000);

        for (Field field : TimeseriesUtils.getDataFieldList(entity)) {
            String name = TimeseriesUtils.getDataFieldName(field);
            ColumnValue value = TimeseriesUtils.getDataFieldColumnValue(field, entity);
            if (!ColumnValue.INTERNAL_NULL_VALUE.equals(value)) {
                row.addField(name, value);
            }
        }

        PutTimeseriesDataRequest request = new PutTimeseriesDataRequest(tableName);
        request.setRows(List.of(row));

        PutTimeseriesDataResponse response = client.putTimeseriesData(request);

        return response.isAllSuccess();
    }
}
