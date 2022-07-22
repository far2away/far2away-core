package com.github.far2away.core.util.generic;

import java.util.Objects;
import java.util.function.Function;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

/**
 * 对象工具类
 *
 * @author far2away
 * @since 2021/10/14
 */
@UtilityClass
public class ObjectUtils {

    /**
     * 取默认值方法，如果给定值为空就取默认值
     *
     * @param value        value
     * @param defaultValue default value
     * @param <T>          class
     * @return result
     */
    public <T> T value(@Nullable T value, T defaultValue) {
        return Objects.nonNull(value) ? value : defaultValue;
    }

    /**
     * 取转换后的值或默认值方法
     * <p>
     * 如果给定值为空就取默认值/不为空就用该值执行转换方法 - 结果不为空直接返回，为空就返回默认值
     * </p>
     *
     * @param <T>             the type parameter
     * @param <R>             the type return value
     * @param value           the value
     * @param convertFunction the convert function
     * @param defaultValue    the default value
     * @return the r
     */
    public <T, R> R value(@Nullable T value, Function<T, R> convertFunction, R defaultValue) {
        if (Objects.nonNull(value)) {
            return ObjectUtils.value(convertFunction.apply(value), defaultValue);
        }
        return defaultValue;
    }

}
