package com.github.far2away.core.util.generic;

import java.util.UUID;
import lombok.experimental.UtilityClass;

/**
 * @author far2away
 * @since 2021/10/14
 */
@UtilityClass
public class UuidUtils {

    public String uuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

}
