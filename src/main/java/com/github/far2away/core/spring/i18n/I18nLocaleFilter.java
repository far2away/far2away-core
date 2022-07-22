package com.github.far2away.core.spring.i18n;

import com.github.far2away.core.util.holder.I18nUtils;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * i18n locale过滤器
 *
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
public class I18nLocaleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String newLocale = I18nUtils.getLocaleFromRequest(request);
        LocaleContextHolder.setLocale(StringUtils.parseLocaleString(newLocale));
        log.debug("i18n_filter_changed_locale_to_{}.", newLocale);
        filterChain.doFilter(request, response);
    }

}
