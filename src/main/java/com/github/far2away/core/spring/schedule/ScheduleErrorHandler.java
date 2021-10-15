package com.github.far2away.core.spring.schedule;

import com.github.far2away.core.definition.constant.StringConstants;
import com.github.far2away.core.util.holder.MetricsUtils;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ErrorHandler;

/**
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
public class ScheduleErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable t) {
        StackTraceElement[] stackTrace = t.getStackTrace();
        Optional<StackTraceElement> stackTraceElementOptional = Optional.ofNullable(stackTrace).map(array -> array[0]);
        String className = stackTraceElementOptional.map(StackTraceElement::getClassName).orElse(StringConstants.HYPHEN);
        String methodName = stackTraceElementOptional.map(StackTraceElement::getMethodName).orElse(StringConstants.HYPHEN);
        //输出日志
        log.error("schedule_exception_4_method_" + className + StringConstants.UNDERSCORE + methodName, t);
        //记录指标
        MetricsUtils.recordUnexpectedExceptionOnTask(t.getClass().getName(), className, methodName);
    }

}
