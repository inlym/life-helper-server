package com.inlym.lifehelper.checklist.service;

import com.inlym.lifehelper.checklist.constant.Color;
import com.inlym.lifehelper.checklist.entity.ChecklistProject;
import com.inlym.lifehelper.checklist.model.ChecklistProjectVO;
import com.inlym.lifehelper.checklist.model.SavingProjectDTO;
import com.mybatisflex.core.util.UpdateEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 待办项目数据转换服务
 *
 * <h2>说明
 * <p>用于处理控制器层的实体对象（Entity）、视图对象（VO）、请求数据（DTO）之间的数据转换。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/2
 * @since 2.3.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ChecklistProjectConverterService {
    /**
     * 从实体对象转化为视图对象
     *
     * @param entity 实体对象
     *
     * @date 2024/7/2
     * @since 2.3.0
     */
    public ChecklistProjectVO convertToVO(ChecklistProject entity) {
        return ChecklistProjectVO.builder()
                .id(entity.getId())
                .createTime(entity.getCreateTime())
                .name(entity.getName())
                .color(entity.getColor())
                .uncompletedTaskCount(entity.getUncompletedTaskCount())
                .build();
    }

    /**
     * 将请求数据对应字段转化为待插入的实体对象
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @date 2024/7/2
     * @since 2.3.0
     */
    public ChecklistProject convertToInsertedEntity(long userId, SavingProjectDTO dto) {
        ChecklistProject inserted = ChecklistProject.builder().name(dto.getName()).color(Color.fromCode(dto.getColorCode())).build();
        if (dto.getFavorite()) {
            inserted.setFavoriteTime(LocalDateTime.now());
        }

        return inserted;
    }

    /**
     * 将请求数据对应字段转化为待更新的实体对象
     *
     * @param userId    用户 ID
     * @param projectId 待办项目 ID
     * @param dto       请求数据
     *
     * @date 2024/7/3
     * @since 2.3.0
     */
    public ChecklistProject convertToUpdatedEntity(long userId, long projectId, SavingProjectDTO dto) {
        ChecklistProject entity = UpdateEntity.of(ChecklistProject.class, projectId);
        entity.setUserId(userId);

        // 处理“项目名称”
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        // 处理“标记颜色”
        if (dto.getColorCode() != null) {
            entity.setColor(Color.fromCode(dto.getColorCode()));
        }
        // 处理“是否收藏”
        if (dto.getFavorite() != null) {
            if (dto.getFavorite()) {
                entity.setFavoriteTime(LocalDateTime.now());
            } else {
                // 将“收藏时间”置空，表示取消收藏
                entity.setFavoriteTime(null);
            }
        }

        return entity;
    }
}
