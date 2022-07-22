package com.github.far2away.core.spring.exception;

import com.github.far2away.core.definition.response.ResponseCode;
import com.github.far2away.core.util.generic.UrlUtils;
import com.github.far2away.core.util.holder.MetricsUtils;
import com.github.far2away.core.util.generic.ResponseUtils;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常拦截： Duplicate　key Exception
 *
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnClass(DuplicateKeyException.class)
public class DuplicateKeyExceptionHandler {

    @PostConstruct
    public void initLog() {
        log.debug("far2away_core_duplicate_key_exception_handler_configured");
    }

    @ExceptionHandler(value = {DuplicateKeyException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleDuplicateKeySqlException(HttpServletRequest request, DuplicateKeyException e) {
        String uri = UrlUtils.getUrlWithoutParamsSilently(request.getRequestURI());
        log.error("global_duplicate_key_exception_4_uri_" + uri, e);
        MetricsUtils.recordGlobalException(uri, e.getClass().getName(), ResponseCode.DUPLICATE_KEY_SQL_ERROR.getResponseCode());
        return ResponseUtils.of(ResponseCode.DUPLICATE_KEY_SQL_ERROR);
    }

}
