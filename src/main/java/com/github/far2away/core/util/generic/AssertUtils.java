package com.github.far2away.core.util.generic;

import com.github.far2away.core.definition.exception.BizException;
import com.github.far2away.core.definition.response.ResponseCodeDetails;
import java.util.Collection;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author far2away
 * @since 2021/10/15
 */
@UtilityClass
public class AssertUtils {

    public void isTrue(@Nullable Boolean b, ResponseCodeDetails responseCodeDetails, Object... args) {
        doAssert(Boolean.TRUE.equals(b), responseCodeDetails, args);
    }

    public void nonNull(@Nullable Object obj, ResponseCodeDetails responseCodeDetails, Object... args) {
        doAssert(Objects.nonNull(obj), responseCodeDetails, args);
    }

    public void notBlank(@Nullable String str, ResponseCodeDetails responseCodeDetails, Object... args) {
        doAssert(StringUtils.hasLength(str), responseCodeDetails, args);
    }

    public void notEmpty(@Nullable Collection<?> collection, ResponseCodeDetails responseCodeDetails, Object... args) {
        doAssert(!CollectionUtils.isEmpty(collection), responseCodeDetails, args);
    }

    private void doAssert(boolean success, ResponseCodeDetails responseCodeDetails, Object... args) {
        if (!success) {
            throw new BizException(responseCodeDetails, args);
        }
    }

}
