package com.inlym.lifehelper.user.info.service;

import com.alicloud.openservices.tablestore.model.*;
import com.inlym.lifehelper.common.base.aliyun.ots.core.model.WideColumnClient;
import com.inlym.lifehelper.common.util.HashedIdUtil;
import com.inlym.lifehelper.user.info.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 用户信息存储库
 *
 * <h2>主要用途
 * <p>管理用户信息的增删改查操作。
 *
 * <h2>注意事项
 * <p>该表与用户 ID 是一一映射关系，因此不存在删除。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/26
 * @since 1.7.0
 **/
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserInfoRepository {
    public static final String TABLE_NAME = "user_info";

    private final WideColumnClient wideColumnClient;

    /**
     * 保存用户信息
     *
     * @param info 用户信息
     *
     * @since 1.7.0
     */
    public void save(UserInfo info) {
        String hashedId = HashedIdUtil.create(info.getUserId());

        // 构造主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("uid", PrimaryKeyValue.fromString(hashedId));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        RowUpdateChange change = new RowUpdateChange(TABLE_NAME, primaryKey);

        // 设置条件：不检查该行是否存在
        Condition condition = new Condition(RowExistenceExpectation.IGNORE);
        change.setCondition(condition);

        // 非空字段进行更新，空字段不做任何处理
        if (info.getNickName() != null) {
            change.put("nick_name", ColumnValue.fromString(info.getNickName()));
        }
        if (info.getAvatarPath() != null) {
            change.put("avatar_path", ColumnValue.fromString(info.getAvatarPath()));
        }
        if (info.getGenderType() != null) {
            change.put("gender_type", ColumnValue.fromLong(info.getGenderType()));
        }
        if (info.getCityId() != null) {
            change.put("city_id", ColumnValue.fromLong(info.getCityId()));
        }

        log.info("[更新用户资料] {}", change.getColumnsToUpdate());

        wideColumnClient.updateRow(new UpdateRowRequest(change));
    }

    /**
     * 通过用户 ID 获取用户信息
     *
     * @param userId 用户 ID
     *
     * @since 1.7.0
     */
    public UserInfo findByUserId(long userId) {
        UserInfo info = UserInfo.builder()
                                .userId(userId)
                                .build();

        // 构造主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("uid", PrimaryKeyValue.fromString(HashedIdUtil.create(userId)));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        // 设置单行查询条件
        SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(TABLE_NAME, primaryKey);
        criteria.setMaxVersions(1);

        GetRowResponse response = wideColumnClient.getRow(new GetRowRequest(criteria));
        Row row = response.getRow();
        log.trace("[查询用户资料] {}", row);

        if (row != null) {
            for (Column column : row.getColumns()) {
                if ("nick_name".equalsIgnoreCase(column.getName())) {
                    info.setNickName(column.getValue()
                                           .asString());
                } else if ("avatar_path".equalsIgnoreCase(column.getName())) {
                    info.setAvatarPath(column.getValue()
                                             .asString());
                } else if ("gender_type".equalsIgnoreCase(column.getName())) {
                    info.setGenderType((int) column.getValue()
                                                   .asLong());
                } else if ("city_id".equalsIgnoreCase(column.getName())) {
                    info.setCityId((int) column.getValue()
                                               .asLong());
                }
            }
        }

        return info;
    }
}
