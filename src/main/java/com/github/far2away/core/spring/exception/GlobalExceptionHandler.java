package com.github.far2away.core.spring.exception;

import com.github.far2away.core.definition.exception.BizException;
import com.github.far2away.core.definition.exception.JsonException;
import com.github.far2away.core.definition.response.ResponseCode;
import com.github.far2away.core.util.generic.UrlUtils;
import com.github.far2away.core.util.holder.MetricsUtils;
import com.github.far2away.core.util.holder.ResponseUtils;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 全局异常统一拦截、Interceptor
 *
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BindException.class, MethodArgumentTypeMismatchException.class,
        HttpMessageNotReadableException.class, MissingServletRequestParameterException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleIllegalRequest(HttpServletRequest request, Exception e) {
        String uri = UrlUtils.getUrlWithoutParamsSilently(request.getRequestURI());
        log.error("global_illegal_request_exception_4_uri_" + uri, e);
        MetricsUtils.recordGlobalException(uri, e.getClass().getName(), ResponseCode.ILLEGAL_REQUEST.getResponseCode());
        return ResponseUtils.of(ResponseCode.ILLEGAL_REQUEST);
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleNotSupportedHttpMethod(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        String uri = UrlUtils.getUrlWithoutParamsSilently(request.getRequestURI());
        log.error("global_unsupported_http_method_exception_4_uri_" + uri, e);
        MetricsUtils.recordGlobalException(uri, e.getClass().getName(), ResponseCode.NOT_SUPPORTED_HTTP_METHOD.getResponseCode());
        return ResponseUtils.of(ResponseCode.NOT_SUPPORTED_HTTP_METHOD);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleParamsValidFailed(HttpServletRequest request, MethodArgumentNotValidException e) {
        String uri = UrlUtils.getUrlWithoutParamsSilently(request.getRequestURI());
        log.error("global_param_valid_exception_4_uri_" + uri, e);
        MetricsUtils.recordGlobalException(uri, e.getClass().getName(), ResponseCode.PARAMS_VALID_FAILED.getResponseCode());
        return ResponseUtils.of(ResponseCode.PARAMS_VALID_FAILED);
    }

    @ExceptionHandler(BizException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleBizException(HttpServletRequest request, BizException e) {
        String uri = UrlUtils.getUrlWithoutParamsSilently(request.getRequestURI());
        log.info("global_biz_exception_{}_{}_{}", e.getCode(), e.getI18nDefaultMessage(), uri);
        MetricsUtils.recordGlobalException(uri, e.getClass().getName(), e.getCode());
        return ResponseUtils.of(e);
    }

    @ExceptionHandler(value = {JsonException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleJsonException(HttpServletRequest request, JsonException e) {
        String uri = UrlUtils.getUrlWithoutParamsSilently(request.getRequestURI());
        log.error("global_json_exception_4_uri_" + uri, e);
        MetricsUtils.recordGlobalException(uri, e.getClass().getName(), ResponseCode.JSON_ERROR.getResponseCode());
        return ResponseUtils.of(ResponseCode.JSON_ERROR);
    }

    @ExceptionHandler(value = {SQLException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleSqlException(HttpServletRequest request, SQLException e) {
        String uri = UrlUtils.getUrlWithoutParamsSilently(request.getRequestURI());
        log.error("global_sql_exception_4_uri_" + uri, e);
        MetricsUtils.recordGlobalException(uri, e.getClass().getName(), ResponseCode.SQL_ERROR.getResponseCode());
        return ResponseUtils.of(ResponseCode.SQL_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleUnknownException(HttpServletRequest request, Exception e) {
        String uri = UrlUtils.getUrlWithoutParamsSilently(request.getRequestURI());
        log.error("global_unknown_exception_4_uri_" + uri, e);
        MetricsUtils.recordGlobalException(uri, e.getClass().getName(), ResponseCode.UNKNOWN_ERROR.getResponseCode());
        return ResponseUtils.of(ResponseCode.UNKNOWN_ERROR);
    }

}
