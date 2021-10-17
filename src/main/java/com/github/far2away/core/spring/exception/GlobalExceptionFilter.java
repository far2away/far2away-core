package com.github.far2away.core.spring.exception;

import com.github.far2away.core.definition.exception.BizException;
import com.github.far2away.core.definition.response.ResponseCode;
import com.github.far2away.core.util.generic.HttpServletResponseUtils;
import com.github.far2away.core.util.generic.UrlUtils;
import com.github.far2away.core.util.holder.JsonUtils;
import com.github.far2away.core.util.holder.MetricsUtils;
import com.github.far2away.core.util.holder.ResponseUtils;
import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 全局统一异常拦截-Filter
 *
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
public class GlobalExceptionFilter extends OncePerRequestFilter {

    @PostConstruct
    public void initLog() {
        log.debug("far2away_core_exception_filter_configured");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (BizException e) {
            String uri = UrlUtils.getUrlWithoutParamsSilently(request.getRequestURI());
            log.error("global_filter_biz_exception_4_uri_" + uri, e);
            MetricsUtils.recordGlobalException(uri, e.getClass().getName(), e.getCode());
            String content = JsonUtils.toJsonString(ResponseUtils.of(e));
            HttpServletResponseUtils.outputSilently(response, content);
        } catch (Exception e) {
            String uri = UrlUtils.getUrlWithoutParamsSilently(request.getRequestURI());
            log.error("global_filter_unknown_exception_4_uri_" + uri, e);
            MetricsUtils.recordGlobalException(uri, e.getClass().getName(), ResponseCode.UNKNOWN_ERROR.getResponseCode());
            String content = JsonUtils.toJsonString(ResponseUtils.of(ResponseCode.UNKNOWN_ERROR));
            HttpServletResponseUtils.outputSilently(response, content);
        }
    }

}
