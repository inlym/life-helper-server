package com.inlym.lifehelper.photoalbum.album;

import com.alicloud.openservices.tablestore.model.*;
import com.inlym.lifehelper.common.base.aliyun.tablestore.TableStoreUtils;
import com.inlym.lifehelper.common.base.aliyun.tablestore.widecolumn.WideColumnClient;
import com.inlym.lifehelper.common.base.aliyun.tablestore.widecolumn.WideColumnTables;
import com.inlym.lifehelper.photoalbum.album.entity.Album;
import com.inlym.lifehelper.photoalbum.album.entity.AlbumColumns;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        List<Album> albumList = new ArrayList<>();

        while (true) {
            GetRangeResponse getRangeResponse = wideColumnClient.getRange(new GetRangeRequest(criteria));
            for (Row row : getRangeResponse.getRows()) {
                Album album = new Album();

                for (PrimaryKeyColumn column : row
                    .getPrimaryKey()
                    .getPrimaryKeyColumns()) {
                    if (AlbumColumns.HASHED_USER_ID.equals(column.getName())) {
                        album.setHashedUserId(column
                            .getValue()
                            .asString());
                    }

                    if (AlbumColumns.ALBUM_ID.equals(column.getName())) {
                        album.setAlbumId(column
                            .getValue()
                            .asString());
                    }
                }

                for (Column column : row.getColumns()) {
                    if (AlbumColumns.NAME.equals(column.getName())) {
                        album.setName(column
                            .getValue()
                            .asString());
                    }

                    if (AlbumColumns.DESCRIPTION.equals(column.getName())) {
                        album.setDescription(column
                            .getValue()
                            .asString());
                    }

                    if (AlbumColumns.DELETED.equals(column.getName())) {
                        album.setDeleted(column
                            .getValue()
                            .asBoolean());
                    }

                    if (AlbumColumns.CREATE_TIME.equals(column.getName())) {
                        album.setCreateTime(column
                            .getValue()
                            .asLong());
                    }

                    if (AlbumColumns.UPDATE_TIME.equals(column.getName())) {
                        album.setUpdateTime(column
                            .getValue()
                            .asLong());
                    }

                    if (AlbumColumns.DELETE_TIME.equals(column.getName())) {
                        album.setDeleteTime(column
                            .getValue()
                            .asLong());
                    }
                }

                albumList.add(album);
            }

            //如果 `nextStartPrimaryKey` 不为 `null`，则继续读取。
            if (getRangeResponse.getNextStartPrimaryKey() != null) {
                criteria.setInclusiveStartPrimaryKey(getRangeResponse.getNextStartPrimaryKey());
            } else {
                break;
            }
        }

        return albumList;
    }
}
