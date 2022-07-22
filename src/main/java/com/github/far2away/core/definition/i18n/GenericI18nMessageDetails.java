package com.github.far2away.core.definition.i18n;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

/**
 * I18nMessageDetails默认实现
 *
 * @author far2away
 * @see I18nMessageDetails
 * @since 2021/10/15
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GenericI18nMessageDetails implements I18nMessageDetails, Serializable {

    private static final long serialVersionUID = -8531242166515404357L;

    private String i18nMessageCode;

    @Nullable
    private Object[] i18nMessageArgs;

    private String i18nDefaultMessage;

}
