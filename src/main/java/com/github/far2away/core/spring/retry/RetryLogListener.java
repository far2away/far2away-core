package com.github.far2away.core.spring.retry;

import com.github.far2away.core.definition.constant.StringConstants;
import java.lang.reflect.Field;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.util.ReflectionUtils;

/**
 * retry增加日志监听
 *
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j(topic = "RETRY_LOG")
public class RetryLogListener implements RetryListener {

    private static final String FIELD_NAME_LABEL = "label";

    private static final String ATTRIBUTE_NAME_CONTEXT_NAME = "context.name";

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        // The 'context.name' attribute has not been set on the context yet. So we have to use reflection.
        Field labelField = ReflectionUtils.findField(callback.getClass(), FIELD_NAME_LABEL);
        if (Objects.isNull(labelField)) {
            return true;
        }
        ReflectionUtils.makeAccessible(labelField);
        String label = (String) ReflectionUtils.getField(labelField, callback);
        int retryCount = context.getRetryCount();
        log.info("retry_start_{}_{}", retryCount, label);
        return true;
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        int retryCount = context.getRetryCount();
        log.info("retry_successfully_{}_{}", retryCount, context.getAttribute(ATTRIBUTE_NAME_CONTEXT_NAME));
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        int retryCount = context.getRetryCount();
        Object method = context.getAttribute(ATTRIBUTE_NAME_CONTEXT_NAME);
        log.warn("retry_error_" + retryCount + StringConstants.UNDERSCORE + method, throwable);
    }

}
