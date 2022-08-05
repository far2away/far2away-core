package com.github.far2away.core.definition.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IgnoreResponseErrorHandler implements ResponseErrorHandler {

    public static final IgnoreResponseErrorHandler INSTANCE = new IgnoreResponseErrorHandler();

    @Override
    public boolean hasError(ClientHttpResponse response) {
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse response) {

    }

}
