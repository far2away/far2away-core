package com.github.far2away.core.definition.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 通用响应码定义，可被国际化
 *
 * @author far2away
 * @since 2021/10/15
 */
@Getter
@RequiredArgsConstructor
public enum ResponseCode implements ResponseCodeDetails {

    //special code
    SUCCESS("00000", "SUCCESS"),
    JSON_ERROR("99995", "系统异常，请稍后重试！"),
    FEIGN_ERROR("99996", "系统异常，请稍后重试！"),
    DUPLICATE_KEY_SQL_ERROR("99997", "系统异常，请稍后重试！"),
    SQL_ERROR("99998", "系统异常，请稍后重试！"),
    UNKNOWN_ERROR("99999", "系统异常，请稍后重试！"),

    //9 common error
    PARAMS_VALID_FAILED("90001", "参数错误！"),
    ILLEGAL_REQUEST("90002", "参数错误！"),
    NOT_SUPPORTED_HTTP_METHOD("90003", "参数错误！"),
    ;

    private final String responseCode;

    private final String i18nDefaultMessage;

    @Override
    public String getI18nMessageCode() {
        return this.getClass().getName() + "." + this.name();
    }

}
