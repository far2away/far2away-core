package com.github.far2away.core.definition.i18n;

import org.springframework.lang.Nullable;

/**
 * i18n消息的组成元素-为枚举类型定义
 *
 * @author far2away
 * @since 2021/10/15
 */
public interface I18nMessageEnumDetails {

    /**
     * 获取i18n code
     *
     * @return the i18n message code
     */
    String getI18nMessageCode();

    /**
     * 获取i18n默认消息
     *
     * @return the i18n default message
     */
    String getI18nDefaultMessage();

    /**
     * 转换为I18nMessageDetails
     *
     * @param args the args
     * @return the 18 n message details
     * @see I18nMessageDetails
     */
    default I18nMessageDetails toI18nMessageDetails(@Nullable Object... args) {
        return new GenericI18nMessageDetails(getI18nMessageCode(), args, getI18nDefaultMessage());
    }

}
