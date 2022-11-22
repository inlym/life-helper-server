package com.inlym.lifehelper.common.base.aliyun.ots.core.utils;

import com.alicloud.openservices.tablestore.model.ColumnValue;
import com.google.common.base.CaseFormat;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.Tag;
import com.inlym.lifehelper.common.base.aliyun.ots.core.annotation.TimeseriesTable;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表格存储时序模型辅助方法
 *
 * <h2>主要用途
 * <p>封装对时序模型实体的相关注解处理用到的方法。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/21
 * @since 1.7.0
 **/
public abstract class TimeseriesUtils {
    /**
     * 获取实体类在时序模型中的表名
     *
     * @param clazz 实体类
     *
     * @since 1.7.0
     */
    public static String getTableName(Class<?> clazz) {
        TimeseriesTable table = clazz.getAnnotation(TimeseriesTable.class);

        // 如果使用了时序模型表注解并指定了表名，则优先级最高
        if (table != null && StringUtils.hasText(table.name())) {
            return table.name();
        }

        // 默认使用实体“类名”的下划线命名作为“表名”
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
    }

    /**
     * 获取实体类在时序模型中的表名
     *
     * @param entity 实体类对象
     *
     * @since 1.7.0
     */
    public static String getTableName(Object entity) {
        return getTableName(entity.getClass());
    }

    /**
     * 获取时间线的度量名称
     *
     * <h2>备注（2022.11.21）
     * <p>本期内使用固定的度量名称，后续会增加度量名称的注解，到时候需要改动当前方法。
     *
     * @param entity 实体类对象
     *
     * @since 1.7.0
     */
    public static String getMeasurementName(Object entity) {
        Class<?> clazz = entity.getClass();
        TimeseriesTable table = clazz.getAnnotation(TimeseriesTable.class);

        String measurementName = table.measurementName();
        if (StringUtils.hasText(measurementName)) {
            return measurementName;
        }

        throw new RuntimeException("measurementName 字段不允许为空！");
    }

    /**
     * 获取数据源信息
     *
     * <h2>备注（2022.11.21）
     * <p>本期内使用固定的数据源名称，后续会增加数据源的注解，到时候需要改动当前方法。
     *
     * @param entity 实体类对象
     *
     * @since 1.7.0
     */
    public static String getDataSource(Object entity) {
        Class<?> clazz = entity.getClass();
        TimeseriesTable table = clazz.getAnnotation(TimeseriesTable.class);

        String dataSource = table.dataSource();
        if (StringUtils.hasText(dataSource)) {
            return dataSource;
        }

        return null;
    }

    /**
     * 获取标签信息字段列表
     *
     * @param entity 实体类对象
     *
     * @since 1.7.0
     */
    private static List<Field> getTagFieldList(Object entity) {
        Class<?> clazz = entity.getClass();

        return Arrays
            .stream(clazz.getDeclaredFields())
            .filter(o -> o.getAnnotation(Tag.class) != null)
            .toList();
    }

    /**
     * 获取数据字段列表
     *
     * <h2>说明
     * <p>数据字段指的是最终写入到时序数据 `fields` 字段的字段。
     *
     * @param entity 实体类对象
     *
     * @see <a href="https://help.aliyun.com/document_detail/341805.html">写入时序数据</a>
     * @since 1.7.0
     */
    public static List<Field> getDataFieldList(Object entity) {
        Class<?> clazz = entity.getClass();

        return Arrays
            .stream(clazz.getDeclaredFields())
            .filter(o -> o.getAnnotation(Tag.class) == null)
            // to do
            // 后续新增了其他注解在此处补充
            .toList();
    }

    /**
     * 获取标签名
     *
     * @param field 字段
     *
     * @since 1.7.0
     */
    private static String getTagName(Field field) {
        Tag tag = field.getAnnotation(Tag.class);

        if (tag != null) {
            if (StringUtils.hasText(tag.name())) {
                return tag.name();
            }

            // 否则直接使用字段名的下划线命名作为列名
            return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
        } else {
            throw new RuntimeException("标签字段应使用 @Tag 标记！");
        }
    }

    /**
     * 获取标签值
     *
     * @param field  字段
     * @param entity 实体对象
     *
     * @since 1.7.0
     */
    @SneakyThrows
    private static String getTagValue(Field field, Object entity) {
        Tag tag = field.getAnnotation(Tag.class);

        if (tag != null) {
            field.setAccessible(true);
            Object value = field.get(entity);
            return value == null ? null : value.toString();
        } else {
            throw new RuntimeException("标签字段应使用 @Tag 标记！");
        }
    }

    /**
     * 构建时间线的标签信息
     *
     * @param entity 实体类对象
     *
     * @since 1.7.0
     */
    public static Map<String, String> buildTags(Object entity) {
        Map<String, String> tags = new HashMap<>(5);
        for (Field field : getTagFieldList(entity)) {
            String name = getTagName(field);
            String value = getTagValue(field, entity);

            if (value == null) {
                throw new RuntimeException("标签值为空！");
            }

            tags.put(name, value);
        }

        return tags;
    }

    /**
     * 获取数据字段的字段名
     *
     * @param field 字段
     *
     * @since 1.7.0
     */
    public static String getDataFieldName(Field field) {
        // todo
        // 后续有专门的注解此处再补充

        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
    }

    /**
     * 获取字段转化后的列值
     *
     * @param field  字段
     * @param entity 实体对象
     *
     * @since 1.7.0
     */
    @SneakyThrows
    public static ColumnValue getDataFieldColumnValue(Field field, Object entity) {
        field.setAccessible(true);
        Object value = field.get(entity);
        return TableStoreUtils.convertToColumnValue(value);
    }
}
