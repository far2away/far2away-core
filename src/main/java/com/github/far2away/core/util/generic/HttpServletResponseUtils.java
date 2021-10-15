package com.github.far2away.core.util.generic;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * @author far2away
 * @since 2021/10/14
 */
@Slf4j
@UtilityClass
public class HttpServletResponseUtils {

    public void outputSilently(HttpServletResponse response, String content) {
        response.setStatus(HttpStatus.OK.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (PrintWriter writer = response.getWriter()) {
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            log.error("failed_output_response_" + content, e);
        }
    }

}
