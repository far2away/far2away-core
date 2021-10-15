package com.github.far2away.core.definition.exception;

/**
 * JSON转换异常
 *
 * @author far2away
 * @since 2021/10/15
 */
public class JsonException extends RuntimeException {

    private static final long serialVersionUID = -86471578410660985L;

    public JsonException(Throwable cause) {
        super(cause);
    }

    public JsonException(String message, Throwable cause) {
        super(message, cause);
    }

}
