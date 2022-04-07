
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

@ControllerAdvice("com.bernardomg.darksouls.explorer")
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

        if (body instanceof ResponseEntity<?>) {
            // Avoid wrapping responses
            result = body;
        } else if (body instanceof Response) {
            // Avoid wrapping responses
            result = body;
        } else if (body instanceof Page<?>) {
            // TODO: Pagination data
            result = toPaginatedResponse((Page<?>) body);
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

}
