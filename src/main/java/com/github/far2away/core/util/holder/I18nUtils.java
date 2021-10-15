package com.github.far2away.core.util.holder;

import com.github.far2away.core.definition.i18n.I18nMessageDetails;
import com.github.far2away.core.definition.i18n.I18nMessageEnumDetails;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;

/**
 * 国际化工具类
 *
 * @author far2away
 * @since 2021/10/14
 */
@UtilityClass
public class I18nUtils {

    public final String LOCALE_REQUEST_HEADER_NAME = "lang";

    private final Set<String> SUPPORTED_LOCALES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("zh_CN", "en_US", "en_GB")));

    public Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

    public String getLocaleFromRequest(HttpServletRequest httpServletRequest) {
        String lang = httpServletRequest.getHeader(LOCALE_REQUEST_HEADER_NAME);
        if (!SUPPORTED_LOCALES.contains(lang)) {
            return LocaleContextHolder.getLocale().toString();
        }
        return lang;
    }

    @Nullable
    public String getI18nMessage(I18nMessageEnumDetails i18nMessageEnumDetails, @Nullable Object... args) {
        I18nMessageDetails i18nMessageDetails = i18nMessageEnumDetails.toI18nMessageDetails(args);
        return getI18nMessage(i18nMessageDetails);
    }

    @Nullable
    public String getI18nMessage(I18nMessageDetails i18nMessageDetails) {
        return getInstance().getMessage(i18nMessageDetails.getI18nMessageCode(),
            i18nMessageDetails.getI18nMessageArgs(), i18nMessageDetails.getI18nDefaultMessage(), getLocale());
    }

    @Nullable
    public String getMessage(String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        return getInstance().getMessage(code, args, defaultMessage, getLocale());
    }

    /**
     * 根据code、参数获取i18n消息，无默认值
     * <note>
     * 根据code找不到message会抛异常
     * </note>
     *
     * @param code the code
     * @param args the args
     * @return the message
     */
    public String getMessage(String code, @Nullable Object[] args) {
        return getInstance().getMessage(code, args, getLocale());
    }

    public MessageSource getInstance() {
        return MessageSourceHolder.INSTANCE;
    }

    private static class MessageSourceHolder {

        private static final MessageSource INSTANCE = SpringUtils.getBean(MessageSource.class);

    }

}
