package com.github.far2away.core.util.holder;

import com.github.far2away.core.definition.exception.BizException;
import com.github.far2away.core.definition.i18n.I18nMessageDetails;
import com.github.far2away.core.definition.response.ResponseCode;
import com.github.far2away.core.definition.response.ResponseCodeDetails;
import com.github.far2away.core.definition.vo.ResponseVo;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

/**
 * 响应Response工具类
 *
 * @author far2away
 * @since 2021/10/14
 */
@UtilityClass
public class ResponseUtils {

    public ResponseVo<Void> success() {
        return of(ResponseCode.SUCCESS);
    }

    public <T> ResponseVo<T> success(@Nullable T data) {
        return of(ResponseCode.SUCCESS, data, new Object[]{});
    }

    public ResponseVo<Void> of(ResponseCodeDetails responseCodeDetails, @Nullable Object... args) {
        return of(responseCodeDetails.getResponseCode(), responseCodeDetails.toI18nMessageDetails(args), null);
    }

    public static <T> ResponseVo<T> of(ResponseCodeDetails responseCodeDetails, @Nullable T data, @Nullable Object[] args) {
        return of(responseCodeDetails.getResponseCode(), responseCodeDetails.toI18nMessageDetails(args), data);
    }

    public static ResponseVo<Void> of(BizException bizException) {
        return of(bizException.getCode(), bizException, null);
    }

    public static <T> ResponseVo<T> of(String code, I18nMessageDetails i18nMessageDetails, @Nullable T data) {
        String locale = I18nUtils.getLocale().toString();
        String tradeId = TraceIdUtils.getTraceId();
        return new ResponseVo<>(code, i18nMessageDetails, locale, tradeId, data);
    }

}
