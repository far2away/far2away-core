package com.github.far2away.core.definition.support;

import com.github.far2away.core.definition.constant.StringConstants;
import com.github.far2away.core.definition.i18n.I18nMessageDetails;
import com.github.far2away.core.util.holder.I18nUtils;
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
        StringBuilder sb = new StringBuilder("Exception in thread " + t.getName());
        if (e instanceof I18nMessageDetails) {
            I18nMessageDetails i18nMessageDetails = (I18nMessageDetails) e;
            String i18nMessage = I18nUtils.getI18nMessage(i18nMessageDetails);
            sb.append(StringConstants.SPACE).append(StringConstants.HYPHEN);
            sb.append(StringConstants.SPACE).append(i18nMessage);
        }
        finalLog.error(sb.toString(), e);
    }

}
