package com.github.far2away.core.definition.i18n;

import org.springframework.lang.Nullable;

/**
 * i18n消息的组成元素定义
 *
 * @author far2away
 * @since 2021/10/15
 */
public interface I18nMessageDetails {

    /**
     * 获取i18n code
     *
     * @return the i 18 n message code
     */
    String getI18nMessageCode();

    /**
     * 获取i18n args
     *
     * @return the object []
     */
    @Nullable
    Object[] getI18nMessageArgs();

    /**
     * 获取i18n默认消息
     *
     * @return the i18n default message
     */
    String getI18nDefaultMessage();

}
