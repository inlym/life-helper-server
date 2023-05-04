package com.inlym.lifehelper.common.base.aliyun.ots.runner;

import com.alicloud.openservices.tablestore.TimeseriesClient;
import com.alicloud.openservices.tablestore.model.TimeseriesTableMeta;
import com.alicloud.openservices.tablestore.model.TimeseriesTableOptions;
import com.alicloud.openservices.tablestore.model.timeseries.CreateTimeseriesTableRequest;
import com.inlym.lifehelper.common.base.aliyun.ots.core.utils.TimeseriesUtils;
import com.inlym.lifehelper.requestlog.entity.RequestLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称
 *
 * <h2>主要用途
 * <p>
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/20
 * @since 1.7.0
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class TimeseriesCreateTablesTask implements ApplicationRunner {
    private final TimeseriesClient client;

    @Override
    public void run(ApplicationArguments args) {
        // 数据库中的表名列表
        List<String> tableNames = client
                .listTimeseriesTable()
                .getTimeseriesTableNames();

        for (TimeseriesTableMeta meta : getTableMetaList()) {
            String tableName = meta.getTimeseriesTableName();
            if (!tableNames.contains(tableName)) {
                // 该表名在数据库已建立的表中没有，说明还没建立，需要建立
                CreateTimeseriesTableRequest request = new CreateTimeseriesTableRequest(meta);
                client.createTimeseriesTable(request);
                log.info("[Timeseries] 新建数据表，表名：{}", tableName);
            }
        }
    }

    /**
     * 获取建表结构数据
     *
     * @since 1.7.0
     */
    private List<TimeseriesTableMeta> getTableMetaList() {
        List<Class<?>> classList = new ArrayList<>();
        List<TimeseriesTableMeta> metaList = new ArrayList<>();

        // 新增的时序模型数据表实体在此处注册
        classList.add(RequestLog.class);

        for (Class<?> clazz : classList) {
            String tableName = TimeseriesUtils.getTableName(clazz);
            int timeToLive = -1;

            TimeseriesTableMeta meta = new TimeseriesTableMeta(tableName);
            meta.setTimeseriesTableOptions(new TimeseriesTableOptions(timeToLive));

            metaList.add(meta);
        }

        return metaList;
    }
}
