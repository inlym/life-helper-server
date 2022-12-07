package com.inlym.lifehelper.common.validation.validator;

import com.inlym.lifehelper.common.validation.LocationString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 经纬度字符串检验器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-22
 * @see LocationString
 * @since 1.0.0
 **/
public class LocationStringValidator implements ConstraintValidator<LocationString, String> {
    /** 经度最小值 */
    public static final Double MIN_LONGITUDE = -180.0;

    /** 经度最大值 */
    public static final Double MAX_LONGITUDE = 180.0;

    /** 纬度最小值 */
    public static final Double MIN_LATITUDE = -85.0;

    /** 纬度最大值 */
    public static final Double MAX_LATITUDE = 85.0;

    /** 经纬度字符串拆分后的数组长度 */
    private static final Integer LIST_LENGTH = 2;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // 允许不传该字段，如果要传则必须符合格式要求
        if (s == null) {
            return true;
        }

        String[] list = s.split(",");
        if (list.length == LIST_LENGTH) {
            double longitude = Double.parseDouble(list[0]);
            double latitude = Double.parseDouble(list[1]);

            return isLongitudeValid(longitude) && isLatitudeValid(latitude);
        }

        return false;
    }

    /**
     * 判断经度值是否有效
     *
     * @param longitude 经度
     */
    private boolean isLongitudeValid(double longitude) {
        return longitude > MIN_LONGITUDE && longitude < MAX_LONGITUDE;
    }

    /**
     * 判断纬度值是否有效
     *
     * @param latitude 纬度
     */
    private boolean isLatitudeValid(double latitude) {
        return latitude > MIN_LATITUDE && latitude < MAX_LATITUDE;
    }
}
