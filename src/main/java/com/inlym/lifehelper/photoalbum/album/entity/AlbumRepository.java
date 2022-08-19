package com.inlym.lifehelper.photoalbum.album.entity;

import com.alicloud.openservices.tablestore.model.*;
import com.alicloud.openservices.tablestore.model.condition.SingleColumnValueCondition;
import com.alicloud.openservices.tablestore.model.filter.SingleColumnValueFilter;
import com.inlym.lifehelper.common.base.aliyun.ots.TableStoreUtils;
import com.inlym.lifehelper.common.base.aliyun.ots.widecolumn.WideColumnClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 相册实体存储库
 *
 * <h2>主要用途
 * <p>管理相册实体的增删改查。注意，不做数据处理，仅存储。
 *
 * <h2>备忘（2022.08.11）
 * <p>临时使用，后续再次统一封装。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/11
 * @since 1.4.0
 **/
@Service
@RequiredArgsConstructor
public class AlbumRepository {
    private final WideColumnClient wideColumnClient;

    /**
     * 将表格存储的行数据转换为实体
     *
     * @param row 表格存储的行数据
     *
     * @since 1.4.0
     */
    private Album getEntityFromRow(Row row) {
        Album album = new Album();

        PrimaryKey primaryKey = row.getPrimaryKey();
        for (PrimaryKeyColumn primaryKeyColumn : primaryKey.getPrimaryKeyColumns()) {
            String columnName = primaryKeyColumn.getName();
            if (columnName.equals(TableStoreCommonColumn.HASHED_USER_ID)) {
                String hashedUserId = primaryKeyColumn
                    .getValue()
                    .asString();
                int userId = TableStoreUtils.parseHashedId(hashedUserId);
                album.setUserId(userId);
            } else if ("album_id".equals(columnName)) {
                album.setAlbumId(primaryKeyColumn
                    .getValue()
                    .asString());
            }
        }

        for (Column column : row.getColumns()) {
            String columnName = column.getName();
            if ("name".equals(columnName)) {
                album.setName(column
                    .getValue()
                    .asString());
            } else if ("description".equals(columnName)) {
                album.setDescription(column
                    .getValue()
                    .asString());
            } else if ("last_upload_time".equals(columnName)) {
                album.setLastUploadTime(column
                    .getValue()
                    .asLong());
            } else if ("create_time".equals(columnName)) {
                album.setCreateTime(column
                    .getValue()
                    .asLong());
            } else if ("update_time".equals(columnName)) {
                album.setUpdateTime(column
                    .getValue()
                    .asLong());
            }
        }

        return album;
    }

    /**
     * 创建相册
     *
     * @param album 相册
     *
     * @since 1.4.0
     */
    public Album create(Album album) {
        String hashedUserId = TableStoreUtils.getHashedId(album.getUserId());

        long now = System.currentTimeMillis();
        album.setCreateTime(now);
        album.setUpdateTime(now);

        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn(TableStoreCommonColumn.HASHED_USER_ID, PrimaryKeyValue.fromString(hashedUserId));
        primaryKeyBuilder.addPrimaryKeyColumn("album_id", PrimaryKeyValue.fromString(album.getAlbumId()));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        RowPutChange rowPutChange = new RowPutChange("album", primaryKey);
        rowPutChange.addColumn("name", ColumnValue.fromString(album.getName()));
        rowPutChange.addColumn("description", ColumnValue.fromString(album.getDescription()));
        rowPutChange.addColumn(TableStoreCommonColumn.CREATE_TIME, ColumnValue.fromLong(now));
        rowPutChange.addColumn(TableStoreCommonColumn.UPDATE_TIME, ColumnValue.fromLong(now));
        wideColumnClient.putRow(new PutRowRequest(rowPutChange));

        return album;
    }

    /**
     * 查询指定用户下的所有相册
     *
     * @param userId 用户 ID
     *
     * @since 1.4.0
     */
    public List<Album> findAll(int userId) {
        String hashedUserId = TableStoreUtils.getHashedId(userId);

        RangeRowQueryCriteria criteria = new RangeRowQueryCriteria("album");

        PrimaryKeyBuilder startPrimaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        startPrimaryKeyBuilder.addPrimaryKeyColumn(TableStoreCommonColumn.HASHED_USER_ID, PrimaryKeyValue.fromString(hashedUserId));
        startPrimaryKeyBuilder.addPrimaryKeyColumn("album_id", PrimaryKeyValue.INF_MIN);
        criteria.setInclusiveStartPrimaryKey(startPrimaryKeyBuilder.build());

        PrimaryKeyBuilder endPrimaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        endPrimaryKeyBuilder.addPrimaryKeyColumn(TableStoreCommonColumn.HASHED_USER_ID, PrimaryKeyValue.fromString(hashedUserId));
        endPrimaryKeyBuilder.addPrimaryKeyColumn("album_id", PrimaryKeyValue.INF_MAX);
        criteria.setExclusiveEndPrimaryKey(endPrimaryKeyBuilder.build());

        // 设置最大读取半本书为 1
        criteria.setMaxVersions(1);

        SingleColumnValueFilter filter = new SingleColumnValueFilter(TableStoreCommonColumn.DELETED, SingleColumnValueFilter.CompareOperator.EQUAL, ColumnValue.fromBoolean(false));
        filter.setLatestVersionsOnly(true);
        filter.setPassIfMissing(true);
        criteria.setFilter(filter);

        List<Album> list = new ArrayList<>();
        while (true) {
            GetRangeResponse response = wideColumnClient.getRange(new GetRangeRequest(criteria));
            for (Row row : response.getRows()) {
                list.add(getEntityFromRow(row));
            }

            // 如果 `nextStartPrimaryKey` 不为 `null`，则继续读取。
            if (response.getNextStartPrimaryKey() != null) {
                criteria.setInclusiveStartPrimaryKey(response.getNextStartPrimaryKey());
            } else {
                break;
            }
        }

        // 按更新时间降序排列
        list.sort(Comparator
            .comparing(Album::getUpdateTime)
            .reversed());

        return list;
    }

    /**
     * 更新相册
     *
     * @param album 相册实体
     *
     * @since 1.4.0
     */
    public Album update(Album album) {
        String hashedUserId = TableStoreUtils.getHashedId(album.getUserId());

        long now = System.currentTimeMillis();
        album.setUpdateTime(now);

        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn(TableStoreCommonColumn.HASHED_USER_ID, PrimaryKeyValue.fromString(hashedUserId));
        primaryKeyBuilder.addPrimaryKeyColumn("album_id", PrimaryKeyValue.fromString(album.getAlbumId()));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        RowUpdateChange change = new RowUpdateChange("album", primaryKey);
        change.put(TableStoreCommonColumn.UPDATE_TIME, ColumnValue.fromLong(now));
        if (album.getName() != null) {
            change.put("name", ColumnValue.fromString(album.getName()));
        }
        if (album.getDescription() != null) {
            change.put("description", ColumnValue.fromString(album.getDescription()));
        }

        wideColumnClient.updateRow(new UpdateRowRequest(change));

        return album;
    }

    /**
     * 软删除一个相册
     *
     * @param userId  用户 ID
     * @param albumId 相册 ID
     *
     * @since 1.4.0
     */
    public void softRemove(int userId, String albumId) {
        String hashedUserId = TableStoreUtils.getHashedId(userId);

        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn(TableStoreCommonColumn.HASHED_USER_ID, PrimaryKeyValue.fromString(hashedUserId));
        primaryKeyBuilder.addPrimaryKeyColumn("album_id", PrimaryKeyValue.fromString(albumId));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        SingleColumnValueCondition singleColumnValueCondition = new SingleColumnValueCondition(TableStoreCommonColumn.DELETED,
            SingleColumnValueCondition.CompareOperator.NOT_EQUAL, ColumnValue.fromBoolean(true));
        singleColumnValueCondition.setLatestVersionsOnly(true);
        singleColumnValueCondition.setPassIfMissing(true);

        Condition condition = new Condition(RowExistenceExpectation.EXPECT_EXIST);
        condition.setColumnCondition(singleColumnValueCondition);

        RowUpdateChange change = new RowUpdateChange("album", primaryKey);
        change.setCondition(condition);

        change.put(TableStoreCommonColumn.DELETED, ColumnValue.fromBoolean(true));
        change.put(TableStoreCommonColumn.DELETE_TIME, ColumnValue.fromLong(System.currentTimeMillis()));

        wideColumnClient.updateRow(new UpdateRowRequest(change));
    }
}
