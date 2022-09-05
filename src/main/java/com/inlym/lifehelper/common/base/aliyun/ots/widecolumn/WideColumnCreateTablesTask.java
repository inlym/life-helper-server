package com.inlym.lifehelper.common.base.aliyun.ots.widecolumn;

import com.alicloud.openservices.tablestore.model.CreateTableRequest;
import com.alicloud.openservices.tablestore.model.PrimaryKeyType;
import com.alicloud.openservices.tablestore.model.TableMeta;
import com.alicloud.openservices.tablestore.model.TableOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 宽表模型数据库的自动建表任务
 *
 * <h2>说明
 * <p>在这里列出需要的表，如果某个表在数据库中不存在，则添加。建立这个自动化任务的主要目的是这样就不需要去后台手工建表了。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/9/6
 * @since 1.4.0
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class WideColumnCreateTablesTask implements ApplicationRunner {
    private final WideColumnClient client;

    @Override
    public void run(ApplicationArguments args) {
        // 数据库中的表名列表
        List<String> tableNames = client
            .listTable()
            .getTableNames();

        for (TableMeta tableMeta : getTableMetaList()) {
            String tableName = tableMeta.getTableName();
            if (!tableNames.contains(tableName)) {
                // 该表名在数据库已建立的表中没有，说明还没建立，需要建立

                //数据的过期时间，单位为秒，-1表示永不过期。带索引表的数据表数据生命周期必须设置为 -1。
                int timeToLive = -1;

                //保存的最大版本数，1表示每列上最多保存一个版本即保存最新的版本。带索引表的数据表最大版本数必须设置为1。
                int maxVersions = 1;

                TableOptions tableOptions = new TableOptions(timeToLive, maxVersions);
                CreateTableRequest request = new CreateTableRequest(tableMeta, tableOptions);
                client.createTable(request);

                log.info("[WideColumn] 新建数据表，表名：{}", tableName);
            }
        }
    }

    /**
     * 获取所有数据表的结构信息
     *
     * <h2>备注（2022.09.06）
     * <p>后续新建数据表，都需要来这里注册。
     *
     * <h2>实体类
     * <li> 1. 相册表 {@link com.inlym.lifehelper.photoalbum.album.entity.Album}
     * <li> 2. 媒体文件表 {@link com.inlym.lifehelper.photoalbum.media.entity.Media}
     *
     * @since 1.4.0
     */
    private List<TableMeta> getTableMetaList() {
        List<TableMeta> list = new ArrayList<>();

        // 备注（2022.09.06）
        // 更加自动化的办法是：只引入“实体类”，由反射获取表名和主键，但这里没必要，因为只需要手动添加一次即可。

        // 相册表
        TableMeta album = new TableMeta("album");
        album.addPrimaryKeyColumn("uid", PrimaryKeyType.STRING);
        album.addPrimaryKeyColumn("album_id", PrimaryKeyType.STRING);
        list.add(album);

        // 相册的媒体文件表
        TableMeta media = new TableMeta("media");
        media.addPrimaryKeyColumn("album_id", PrimaryKeyType.STRING);
        media.addPrimaryKeyColumn("media_id", PrimaryKeyType.STRING);
        list.add(media);

        return list;
    }
}
