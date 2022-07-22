package com.github.far2away.core.definition.response;

import com.github.far2away.core.definition.i18n.I18nMessageEnumDetails;

/**
 * 可被国际化的通用响应码接口定义
 *
 * @author far2away
 * @since 2021/10/15
 */
public interface ResponseCodeDetails extends I18nMessageEnumDetails {

    String getResponseCode();

}
