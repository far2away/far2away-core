package com.github.far2away.core.spring.p6spy;

import com.p6spy.engine.spy.P6ModuleManager;
import com.p6spy.engine.spy.P6SpyOptions;
import java.lang.reflect.Field;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ReflectionUtils;

/**
 * p6spy初始化器
 *
 * @author far2away
 * @since 2021/10/15
 */
@RequiredArgsConstructor
public class P6spyInitializer {

    private final P6spyProperties p6spyProperties;

    @PostConstruct
    public void init() throws IllegalAccessException {
        Map<String, String> defaults = P6SpyOptions.getActiveInstance().getDefaults();
        Field[] fields = P6spyProperties.class.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            ReflectionUtils.makeAccessible(field);
            defaults.put(fieldName, String.valueOf(field.get(p6spyProperties)));
        }
        P6SpyOptions.getActiveInstance().load(defaults);
        P6ModuleManager.getInstance().reload();
    }

}
