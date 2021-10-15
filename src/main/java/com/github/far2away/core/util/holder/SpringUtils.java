package com.github.far2away.core.util.holder;

import com.github.far2away.core.spring.context.SpringContextHolder;
import lombok.experimental.UtilityClass;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;

/**
 * Spring工具类，从spring容器中获取对象
 *
 * @author far2away
 * @since 2021/10/15
 */
@UtilityClass
public class SpringUtils {

    public <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    @Nullable
    public <T> T getBeanSilently(Class<T> clazz) {
        try {
            ApplicationContext applicationContext = getApplicationContext();
            return applicationContext.getBean(clazz);
        } catch (Exception e) {
            return null;
        }
    }

    private ApplicationContext getApplicationContext() {
        return SpringContextHolder.getApplicationContext();
    }

}
