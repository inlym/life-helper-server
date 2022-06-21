package com.inlym.lifehelper.photoalbum.album;

import com.alicloud.openservices.tablestore.model.*;
import com.alicloud.openservices.tablestore.model.filter.SingleColumnValueFilter;
import com.inlym.lifehelper.common.base.aliyun.tablestore.TableStoreUtils;
import com.inlym.lifehelper.common.base.aliyun.tablestore.widecolumn.WideColumnClient;
import com.inlym.lifehelper.common.base.aliyun.tablestore.widecolumn.WideColumnTables;
import com.inlym.lifehelper.photoalbum.album.entity.Album;
import com.inlym.lifehelper.photoalbum.album.entity.AlbumColumns;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 相册服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/13
 * @since 1.3.0
 **/
@Service
@RequiredArgsConstructor
public class AlbumService {
    private final WideColumnClient wideColumnClient;

    /**
     * 创建相册
     *
     * @param album 相册实体
     *
     * @since 1.3.0
     */
    public String create(Album album) {
        String id = TableStoreUtils.getRandomId();
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn(AlbumColumns.HASHED_USER_ID, PrimaryKeyValue.fromString(album.getHashedUserId()));
        primaryKeyBuilder.addPrimaryKeyColumn(AlbumColumns.ALBUM_ID, PrimaryKeyValue.fromString(id));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        RowPutChange rowPutChange = new RowPutChange(WideColumnTables.ALBUM, primaryKey);
        rowPutChange.addColumn(AlbumColumns.NAME, ColumnValue.fromString(album.getName()));
        rowPutChange.addColumn(AlbumColumns.DESCRIPTION, ColumnValue.fromString(album.getDescription()));
        rowPutChange.addColumn(AlbumColumns.DELETED, ColumnValue.fromBoolean(false));

        long now = System.currentTimeMillis();
        rowPutChange.addColumn(AlbumColumns.CREATE_TIME, ColumnValue.fromLong(now));
        rowPutChange.addColumn(AlbumColumns.UPDATE_TIME, ColumnValue.fromLong(now));
        wideColumnClient.putRow(new PutRowRequest(rowPutChange));

        return id;
    }

    /**
     * 删除一个相册（逻辑删除）
     *
     * @param album 相册实体
     */
    public void delete(Album album) {
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn(AlbumColumns.HASHED_USER_ID, PrimaryKeyValue.fromString(album.getHashedUserId()));
        primaryKeyBuilder.addPrimaryKeyColumn(AlbumColumns.ALBUM_ID, PrimaryKeyValue.fromString(album.getAlbumId()));

        RowUpdateChange change = new RowUpdateChange(WideColumnTables.ALBUM, primaryKeyBuilder.build());
        change.put(AlbumColumns.DELETED, ColumnValue.fromBoolean(true));
        change.put(AlbumColumns.DELETE_TIME, ColumnValue.fromLong(System.currentTimeMillis()));

        wideColumnClient.updateRow(new UpdateRowRequest(change));
    }

    /**
     * 获取用户的所有相册
     *
     * @param userId 用户 ID
     *
     * @since 1.3.0
     */
    public List<Album> list(int userId) {
        String uid = TableStoreUtils.getHashedId(userId);

        RangeRowQueryCriteria criteria = new RangeRowQueryCriteria(WideColumnTables.ALBUM);

        PrimaryKeyBuilder startPrimaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        startPrimaryKeyBuilder.addPrimaryKeyColumn(AlbumColumns.HASHED_USER_ID, PrimaryKeyValue.fromString(uid));
        startPrimaryKeyBuilder.addPrimaryKeyColumn(AlbumColumns.ALBUM_ID, PrimaryKeyValue.INF_MIN);
        criteria.setInclusiveStartPrimaryKey(startPrimaryKeyBuilder.build());

        PrimaryKeyBuilder endPrimaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        endPrimaryKeyBuilder.addPrimaryKeyColumn(AlbumColumns.HASHED_USER_ID, PrimaryKeyValue.fromString(uid));
        endPrimaryKeyBuilder.addPrimaryKeyColumn(AlbumColumns.ALBUM_ID, PrimaryKeyValue.INF_MAX);
        criteria.setExclusiveEndPrimaryKey(endPrimaryKeyBuilder.build());

        criteria.setMaxVersions(1);

        SingleColumnValueFilter filter = new SingleColumnValueFilter(AlbumColumns.DELETED, SingleColumnValueFilter.CompareOperator.EQUAL, ColumnValue.fromBoolean(false));
        filter.setLatestVersionsOnly(true);
        filter.setPassIfMissing(true);
        criteria.setFilter(filter);

        List<Album> albumList = new ArrayList<>();

        while (true) {
            GetRangeResponse getRangeResponse = wideColumnClient.getRange(new GetRangeRequest(criteria));
            for (Row row : getRangeResponse.getRows()) {
                Album album = TableStoreUtils.buildEntity(row, Album.class);
                albumList.add(album);
            }

            //如果 `nextStartPrimaryKey` 不为 `null`，则继续读取。
            if (getRangeResponse.getNextStartPrimaryKey() != null) {
                criteria.setInclusiveStartPrimaryKey(getRangeResponse.getNextStartPrimaryKey());
            } else {
                break;
            }
        }

        // 按更新时间降序排列
        albumList.sort(Comparator
            .comparing(Album::getUpdateTime)
            .reversed());

        return albumList;
    }
}
