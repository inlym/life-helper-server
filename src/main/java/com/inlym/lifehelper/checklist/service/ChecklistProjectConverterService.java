package com.inlym.lifehelper.checklist.service;

import com.inlym.lifehelper.checklist.constant.Color;
import com.inlym.lifehelper.checklist.entity.ChecklistProject;
import com.inlym.lifehelper.checklist.model.ChecklistProjectVO;
import com.inlym.lifehelper.checklist.model.SavingProjectDTO;
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
}
