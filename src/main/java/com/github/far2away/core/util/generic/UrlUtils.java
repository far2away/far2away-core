package com.github.far2away.core.util.generic;

import java.net.URI;
import java.net.URISyntaxException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * @author far2away
 * @since 2021/10/14
 */
@Slf4j
@UtilityClass
public class UrlUtils {

    public String getUrlWithoutParamsSilently(String url) {
        try {
            return getUrlWithoutParams(url);
        } catch (URISyntaxException e) {
            log.warn("url_utils_failed_remove_params_4_url_{}", url);
        }
        return url;
    }

    public String getUrlWithoutParams(String url) throws URISyntaxException {
        URI uri = new URI(url);
        return new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), null, uri.getFragment()).toString();
    }

}
