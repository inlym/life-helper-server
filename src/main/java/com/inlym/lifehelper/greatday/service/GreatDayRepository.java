package com.inlym.lifehelper.greatday.service;

import com.alicloud.openservices.tablestore.model.*;
import com.inlym.lifehelper.common.base.aliyun.ots.core.model.WideColumnClient;
import com.inlym.lifehelper.common.util.HashedIdUtil;
import com.inlym.lifehelper.greatday.entity.GreatDay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 纪念日实体存储库
 *
 * <h2>主要用途
 * <p>管理纪念日模块数据的增删改查。
 *
 * <h2>备忘（2023.05.27）
 * <p>之前自己封装了一套利用反射代理可自定义处理字段，暂时先不用，这里先用最原生的方法写，后续再考虑整合优化。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/7
 * @since 1.8.0
 **/
@Repository
@Slf4j
@RequiredArgsConstructor
public class GreatDayRepository {
    /** 数据表名 */
    public static final String TABLE_NAME = "great_day_2";

    private final WideColumnClient client;

    /**
     * 创建数据表
     *
     * @date 2023/5/26
     * @since 2.0.0
     */
    public void createTable() {
        TableMeta tableMeta = new TableMeta(TABLE_NAME);
        // 哈希化的用户 ID 作为分区键
        tableMeta.addPrimaryKeyColumn("uid", PrimaryKeyType.STRING);
        // 第二主键列设置自增
        tableMeta.addPrimaryKeyColumn("dayId", PrimaryKeyType.INTEGER, PrimaryKeyOption.AUTO_INCREMENT);

        // 数据永不过期
        int timeToLive = -1;
        // 只保存一个数据版本
        int maxVersions = 1;

        TableOptions tableOptions = new TableOptions(timeToLive, maxVersions);
        CreateTableRequest request = new CreateTableRequest(tableMeta, tableOptions);

        client.createTable(request);
    }

    /**
     * 新增一条数据
     *
     * @param greatDay 纪念日实体对象
     *
     * @return 纪念日实体对象（已补充自增 ID）
     *
     * @date 2023/5/26
     * @since 2.0.0
     */
    public GreatDay create(GreatDay greatDay) {
        // 以哈希化的用户 ID 作为分区键
        String hashedId = HashedIdUtil.create(greatDay.getUserId());

        // 构造主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("uid", PrimaryKeyValue.fromString(hashedId));
        primaryKeyBuilder.addPrimaryKeyColumn("dayId", PrimaryKeyValue.AUTO_INCREMENT);
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        RowPutChange change = new RowPutChange(TABLE_NAME, primaryKey);
        change.setReturnType(ReturnType.RT_PK);

        // 非空字段进行处理，空字段不做任何处理
        if (greatDay.getName() != null) {
            change.addColumn("name", ColumnValue.fromString(greatDay.getName()));
        }
        if (greatDay.getDate() != null) {
            change.addColumn("date", ColumnValue.fromString(greatDay
                                                                .getDate()
                                                                .toString()));
        }
        if (greatDay.getIcon() != null) {
            change.addColumn("icon", ColumnValue.fromString(greatDay.getIcon()));
        }

        PutRowResponse response = client.putRow(new PutRowRequest(change));

        long dayId = response
            .getRow()
            .getPrimaryKey()
            .getPrimaryKeyColumn("dayId")
            .getValue()
            .asLong();

        greatDay.setDayId(dayId);
        log.info("[纪念日][新增] {}", greatDay);

        return greatDay;
    }

    /**
     * 更新（编辑）
     *
     * @param greatDay 纪念日实体对象
     *
     * @date 2023/5/26
     * @since 2.0.0
     */
    public void update(GreatDay greatDay) {
        // 以哈希化的用户 ID 作为分区键
        String hashedId = HashedIdUtil.create(greatDay.getUserId());

        // 构造主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("uid", PrimaryKeyValue.fromString(hashedId));
        primaryKeyBuilder.addPrimaryKeyColumn("dayId", PrimaryKeyValue.fromLong(greatDay.getDayId()));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        RowUpdateChange change = new RowUpdateChange(TABLE_NAME, primaryKey);

        // 非空字段进行处理，空字段不做任何处理
        if (greatDay.getName() != null) {
            change.put("name", ColumnValue.fromString(greatDay.getName()));
        }
        if (greatDay.getDate() != null) {
            change.put("date", ColumnValue.fromString(greatDay
                                                          .getDate()
                                                          .toString()));
        }
        if (greatDay.getIcon() != null) {
            change.put("icon", ColumnValue.fromString(greatDay.getIcon()));
        }

        log.info("[纪念日][更新] {}", greatDay);
        client.updateRow(new UpdateRowRequest(change));
    }

    /**
     * 物理删除一条记录
     *
     * @param userId 用户 ID
     * @param dayId  纪念日 ID
     *
     * @date 2023/5/26
     * @since 2.0.0
     */
    public void delete(int userId, long dayId) {
        // 以哈希化的用户 ID 作为分区键
        String hashedId = HashedIdUtil.create(userId);

        // 构造主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("uid", PrimaryKeyValue.fromString(hashedId));
        primaryKeyBuilder.addPrimaryKeyColumn("dayId", PrimaryKeyValue.fromLong(dayId));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        RowDeleteChange change = new RowDeleteChange(TABLE_NAME, primaryKey);

        log.info("[纪念日][删除] userId={}, dayId={}", userId, dayId);
        client.deleteRow(new DeleteRowRequest(change));
    }

    /**
     * 根据用户 ID 查找所有数据
     *
     * @param userId 用户 ID
     *
     * @return 实体对象列表（若为空，则返回空列表而不是 `null`）
     *
     * @date 2023/5/26
     * @since 2.0.0
     */
    public List<GreatDay> findAll(int userId) {
        // 以哈希化的用户 ID 作为分区键
        String hashedId = HashedIdUtil.create(userId);

        RangeRowQueryCriteria criteria = new RangeRowQueryCriteria(TABLE_NAME);
        criteria.setMaxVersions(1);
        // 设置起始主键
        PrimaryKeyBuilder startPrimaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        startPrimaryKeyBuilder.addPrimaryKeyColumn("uid", PrimaryKeyValue.fromString(hashedId));
        startPrimaryKeyBuilder.addPrimaryKeyColumn("dayId", PrimaryKeyValue.INF_MAX);
        criteria.setInclusiveStartPrimaryKey(startPrimaryKeyBuilder.build());
        // 设置结束主键
        PrimaryKeyBuilder endPrimaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        endPrimaryKeyBuilder.addPrimaryKeyColumn("uid", PrimaryKeyValue.fromString(hashedId));
        endPrimaryKeyBuilder.addPrimaryKeyColumn("dayId", PrimaryKeyValue.INF_MIN);
        criteria.setInclusiveStartPrimaryKey(endPrimaryKeyBuilder.build());

        List<GreatDay> list = new ArrayList<>();

        while (true) {
            GetRangeResponse response = client.getRange(new GetRangeRequest(criteria));
            for (Row row : response.getRows()) {
                list.add(convert(row));
            }

            if (response.getNextStartPrimaryKey() != null) {
                log.trace("列表未完成，下一主键 {}", response.getNextStartPrimaryKey());
                criteria.setInclusiveStartPrimaryKey(response.getNextStartPrimaryKey());
            } else {
                break;
            }
        }

        return list;
    }

    /**
     * 将宽表模型查询到的“行记录”转化为实体对象
     *
     * @param row 宽表模型的行记录
     *
     * @return 实体对象
     *
     * @date 2023/5/27
     * @since 2.0.0
     */
    private GreatDay convert(Row row) {
        GreatDay day = new GreatDay();

        // 先处理主键列，一定不为空，直接按列名取值
        String uid = row
            .getPrimaryKey()
            .getPrimaryKeyColumn("uid")
            .getValue()
            .asString();

        long dayId = row
            .getPrimaryKey()
            .getPrimaryKeyColumn("dayId")
            .getValue()
            .asLong();

        day.setUserId(HashedIdUtil.parse(uid));
        day.setDayId(dayId);

        // 属性列和实体对象的字段比较，很可能不一定，使用循环
        for (Column column : row.getColumns()) {
            if ("name".equals(column.getName())) {
                day.setName(column
                                .getValue()
                                .asString());
            } else if ("date".equals(column.getName())) {
                day.setDate(LocalDate.parse(column
                                                .getValue()
                                                .asString()));
            } else if ("icon".equals(column.getName())) {
                day.setIcon(column
                                .getValue()
                                .asString());
            } else {
                log.trace("未使用到的列，列名={}，值={}", column.getName(), column
                    .getValue()
                    .asString());
            }
        }

        return day;
    }

    /**
     * 通过主键列找到对应实体对象数据
     *
     * @param userId 用户 ID
     * @param dayId  纪念日 ID
     *
     * @return 查找到的实体对象（若不存在则返回 `null`）
     *
     * @date 2023/5/27
     * @since 2.0.0
     */
    public GreatDay findOne(int userId, long dayId) {
        // 以哈希化的用户 ID 作为分区键
        String hashedId = HashedIdUtil.create(userId);

        // 构造主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("uid", PrimaryKeyValue.fromString(hashedId));
        primaryKeyBuilder.addPrimaryKeyColumn("dayId", PrimaryKeyValue.fromLong(dayId));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(TABLE_NAME, primaryKey);
        criteria.setMaxVersions(1);

        GetRowResponse response = client.getRow(new GetRowRequest(criteria));
        Row row = response.getRow();
        if (row == null) {
            return null;
        }

        return convert(row);
    }
}
