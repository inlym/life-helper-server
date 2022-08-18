package com.inlym.lifehelper.common.base.aliyun.ots.widecolumn;

import com.alicloud.openservices.tablestore.model.PrimaryKey;
import com.alicloud.openservices.tablestore.model.PrimaryKeyBuilder;
import com.google.common.base.CaseFormat;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.PrimaryKeyColumn;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 表格存储宽表模型存储库
 *
 * <h2>主要用途
 * <p>封装增删改查操作。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/18
 * @since 1.4.0
 **/
@Service
@RequiredArgsConstructor
public class WideColumnRepository<T> {
    private final WideColumnClient client;

    /**
     * 获取实体对象在表格存储中使用的表名
     *
     * <h2>说明
     * <p>默认使用类名的下划线命名作为表名，可使用 {@link Table} 注解的 {@link Table#name()} 属性覆盖默认值。
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    private String getTableName(T entity) {
        Table table = entity
            .getClass()
            .getAnnotation(Table.class);

        // 如果使用了 {@link Table} 注解并指定了表名，则取指定的表名
        if (table != null && StringUtils.hasLength(table.name())) {
            return table.name();
        }

        // 否则直接使用“类名”的下划线命名作为“表名”
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entity
            .getClass()
            .getSimpleName());
    }

    /**
     * 构建主键
     *
     * @param entity 实体对象
     *
     * @since 1.4.0
     */
    private PrimaryKey buildPrimaryKey(T entity) {
        // 主键列字段的列表
        List<Field> fields = new ArrayList<>();

        // 遍历实体对象的所有字段，将有主键列注解的字段添加到该列表中
        for (Field field : entity
            .getClass()
            .getDeclaredFields()) {
            if (field.getAnnotation(PrimaryKeyColumn.class) != null) {
                fields.add(field);
            }
        }

        // 对列表重排序，按照 `order` 字段升序排列
        fields.sort(Comparator.comparingInt(o -> o
            .getAnnotation(PrimaryKeyColumn.class)
            .order()));

        // 主键构建器
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();

        // ... to do

        return null;
    }
}
