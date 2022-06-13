package com.inlym.lifehelper.photoalbum;

import com.alicloud.openservices.tablestore.model.*;
import com.inlym.lifehelper.common.base.aliyun.tablestore.TableStoreUtils;
import com.inlym.lifehelper.common.base.aliyun.tablestore.widecolumn.WideColumnClient;
import com.inlym.lifehelper.common.base.aliyun.tablestore.widecolumn.WideColumnTables;
import com.inlym.lifehelper.photoalbum.entity.Album;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        primaryKeyBuilder.addPrimaryKeyColumn(WideColumnTables.Album.USER_ID, PrimaryKeyValue.fromString(TableStoreUtils.getNonClusteredId(album.getUserId())));
        primaryKeyBuilder.addPrimaryKeyColumn(WideColumnTables.Album.ID, PrimaryKeyValue.fromString(id));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        RowPutChange rowPutChange = new RowPutChange(WideColumnTables.ALBUM, primaryKey);
        rowPutChange.addColumn(WideColumnTables.Album.NAME, ColumnValue.fromString(album.getName()));
        rowPutChange.addColumn(WideColumnTables.Album.DESCRIPTION, ColumnValue.fromString(album.getDescription()));
        wideColumnClient.putRow(new PutRowRequest(rowPutChange));

        return id;
    }
}
