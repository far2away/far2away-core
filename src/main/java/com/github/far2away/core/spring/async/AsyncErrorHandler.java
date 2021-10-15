package com.github.far2away.core.spring.async;

import com.github.far2away.core.util.holder.MetricsUtils;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

/**
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
public class AsyncErrorHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        String methodName = method.getName();
        //输出日志
        log.error("async_exception_4_method_" + methodName, ex);
        //记录指标
        MetricsUtils.recordUnexpectedExceptionOnAsync(ex.getClass().getName(), methodName);
    }

}
