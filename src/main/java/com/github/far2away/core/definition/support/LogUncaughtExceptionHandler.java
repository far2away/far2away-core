package com.github.far2away.core.definition.support;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.lang.Nullable;

@Slf4j
public class LogUncaughtExceptionHandler implements UncaughtExceptionHandler {

    public static final LogUncaughtExceptionHandler INSTANCE = new LogUncaughtExceptionHandler(null);

    private final Logger finalLog;

    /**
     * @param externalLog external slf4j log. if null, use LogUncaughtExceptionHandler.log
     */
    public LogUncaughtExceptionHandler(@Nullable Logger externalLog) {
        if (Objects.nonNull(externalLog)) {
            this.finalLog = externalLog;
        } else {
            this.finalLog = log;
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        finalLog.error("Exception in thread " + t.getName(), e);
    }

}
