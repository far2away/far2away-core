package com.github.far2away.core.spring.scheduling;

import com.github.far2away.core.definition.constant.StringConstants;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ErrorHandler;

/**
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
public class SchedulingErrorHandlerCustomizer implements ErrorHandler {

    @Override
    public void handleError(Throwable t) {
        StackTraceElement[] stackTrace = t.getStackTrace();
        Optional<StackTraceElement> stackTraceElementOptional = Optional.ofNullable(stackTrace).map(array -> array[0]);
        String className = stackTraceElementOptional.map(StackTraceElement::getClassName).orElse(StringConstants.HYPHEN);
        String methodName = stackTraceElementOptional.map(StackTraceElement::getMethodName).orElse(StringConstants.HYPHEN);
        String lineNumber = stackTraceElementOptional.map(StackTraceElement::getLineNumber).map(String::valueOf).orElse(StringConstants.HYPHEN);
        //输出日志
        log.error("schedule_exception_4_" + className + StringConstants.UNDERSCORE + methodName + StringConstants.UNDERSCORE + lineNumber, t);
    }

}
