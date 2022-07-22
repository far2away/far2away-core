package com.github.far2away.core.util.generic;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

/**
 * 函数lambda工具类
 *
 * @author far2away
 * @since 2021/10/14
 */
@UtilityClass
public class FunctionUtils {

    /**
     * 把集合变成变成stream，集合为null时变成空stream
     *
     * @param <T>        the type parameter
     * @param collection the collection
     * @return the stream
     */
    public <T> Stream<T> nullAsEmptyStream(@Nullable Collection<T> collection) {
        if (Objects.isNull(collection)) {
            return Stream.empty();
        }
        return collection.stream();
    }

    /**
     * 获取集合中的第一个元素
     *
     * @param <T>        the type parameter
     * @param collection the collection
     * @return the t
     */
    @Nullable
    public <T> T getFirstElement(@Nullable Collection<T> collection) {
        return getFirstOptionalElement(collection).orElse(null);
    }

    /**
     * 获取集合中的第一个被Optional包装的元素
     *
     * @param <T>        the type parameter
     * @param collection the collection
     * @return the first optional element
     */
    public <T> Optional<T> getFirstOptionalElement(@Nullable Collection<T> collection) {
        if (Objects.isNull(collection)) {
            return Optional.empty();
        }
        return collection.stream().findFirst();
    }

    /**
     * BinaryOperator实现，用最新元素替换旧元素
     *
     * @param <T> the type parameter
     * @return the binary operator
     * @see Collectors#toMap(java.util.function.Function, java.util.function.Function, java.util.function.BinaryOperator)
     */
    public <T> BinaryOperator<T> latestMergeFunction() {
        return (existing, replacement) -> replacement;
    }

}
