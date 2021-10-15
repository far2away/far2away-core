package com.github.far2away.core.definition.exception;

import com.github.far2away.core.definition.i18n.I18nMessageDetails;
import com.github.far2away.core.definition.response.ResponseCodeDetails;
import lombok.Getter;
import org.springframework.lang.Nullable;

/**
 * 通用业务异常
 *
 * @author far2away
 * @since 2021/10/15
 */
public class BizException extends RuntimeException implements I18nMessageDetails {

    private static final long serialVersionUID = -5448388503723841532L;

    @Getter
    private final String code;

    private final I18nMessageDetails i18nMessageDetails;

    public BizException(ResponseCodeDetails responseCodeDetails, @Nullable Object... args) {
        super(responseCodeDetails.getI18nDefaultMessage());
        this.code = responseCodeDetails.getResponseCode();
        this.i18nMessageDetails = responseCodeDetails.toI18nMessageDetails(args);
    }

    @Override
    public String getI18nMessageCode() {
        return i18nMessageDetails.getI18nMessageCode();
    }

    @Override
    public Object[] getI18nMessageArgs() {
        return i18nMessageDetails.getI18nMessageArgs();
    }

    @Override
    public String getI18nDefaultMessage() {
        return i18nMessageDetails.getI18nDefaultMessage();
    }

}
