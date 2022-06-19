
package com.bernardomg.darksouls.explorer.response.controller;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.bernardomg.darksouls.explorer.response.model.DefaultPaginatedResponse;
import com.bernardomg.darksouls.explorer.response.model.DefaultResponse;
import com.bernardomg.darksouls.explorer.response.model.PaginatedResponse;
import com.bernardomg.darksouls.explorer.response.model.Response;
import com.bernardomg.pagination.model.PageIterable;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice("com.bernardomg.darksouls.explorer")
@Slf4j
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    public ResponseAdvice() {
        super();
    }

    @Override
    public Object beforeBodyWrite(final Object body,
            final MethodParameter returnType,
            final MediaType selectedContentType,
            final Class<? extends HttpMessageConverter<?>> selectedConverterType,
            final ServerHttpRequest request,
            final ServerHttpResponse response) {
        final Object result;

        log.trace("Received {} as response body", body);
        if (body instanceof ResponseEntity<?>) {
            // Avoid wrapping responses
            result = body;
        } else if (body instanceof Response) {
            // Avoid wrapping responses
            result = body;
        } else if (body instanceof Page<?>) {
            result = toPaginatedResponse((Page<?>) body);
        } else if (body instanceof PageIterable<?>) {
            result = toPaginatedResponse((PageIterable<?>) body);
        } else if (body == null) {
            log.debug("Received null as response body");
            result = new DefaultResponse<>();
        } else {
            result = new DefaultResponse<>(body);
        }

        return result;
    }

    @Override
    public boolean supports(final MethodParameter returnType,
            final Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    private final PaginatedResponse<?> toPaginatedResponse(final Page<?> page) {
        final DefaultPaginatedResponse<?> paginatedResponse;

        paginatedResponse = new DefaultPaginatedResponse<>(page.getContent());
        paginatedResponse.setElementsInPage(page.getNumberOfElements());
        paginatedResponse.setTotalElements(page.getTotalElements());
        paginatedResponse.setTotalPages(page.getTotalPages());
        paginatedResponse.setPageNumber(page.getNumber());
        paginatedResponse.setSize(page.getSize());
        paginatedResponse.setFirst(page.isFirst());
        paginatedResponse.setLast(page.isLast());

        return paginatedResponse;
    }

    private final PaginatedResponse<?>
            toPaginatedResponse(final PageIterable<?> page) {
        final DefaultPaginatedResponse<?> paginatedResponse;

        paginatedResponse = new DefaultPaginatedResponse<>(page.getContent());
        paginatedResponse.setElementsInPage(page.getElementsInPage());
        paginatedResponse.setTotalElements(page.getTotalElements());
        paginatedResponse.setTotalPages(page.getTotalPages());
        paginatedResponse.setPageNumber(page.getPageNumber());
        paginatedResponse.setSize(page.getSize());
        paginatedResponse.setFirst(page.isFirst());
        paginatedResponse.setLast(page.isLast());

        return paginatedResponse;
    }

}
