package com.github.far2away.core.spring.async;

import java.lang.Thread.UncaughtExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

@RequiredArgsConstructor
public class LogUncaughtExceptionHandler implements UncaughtExceptionHandler {

    private final Logger log;

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("Exception in thread " + t.getName(), e);
    }

}
