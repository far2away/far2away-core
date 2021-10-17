package com.github.far2away.core.spring.i18n;

import com.github.far2away.core.util.holder.I18nUtils;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * i18n locale拦截器
 *
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
public class I18nLocaleChangeInterceptor extends LocaleChangeInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String newLocale = I18nUtils.getLocaleFromRequest(request);

        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if (Objects.isNull(localeResolver)) {
            log.warn("failed_change_locale_1_locale_resolver_not_found");
        } else {
            localeResolver.setLocale(request, response, StringUtils.parseLocaleString(newLocale));
            log.debug("successfully_changed_interceptor_locale_to_{}.", newLocale);
        }
        return true;
    }

}
