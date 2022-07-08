
package com.bernardomg.pagination.argument;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bernardomg.pagination.model.DefaultPagination;
import com.bernardomg.pagination.model.DisabledPagination;
import com.bernardomg.pagination.model.Pagination;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PaginationArgumentResolver implements HandlerMethodArgumentResolver {

    public PaginationArgumentResolver() {
        super();
    }

    @Override
    public final Pagination resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        final String     pageText;
        final String     sizeText;
        final Integer    page;
        final Integer    size;
        final Pagination pagination;

        pageText = webRequest.getParameter("page");

        if (pageText == null) {
            // No pagination
            pagination = new DisabledPagination();
            log.trace("No pagination received, using disabled pagination");
        } else {
            page = Integer.valueOf(pageText);

            // Parses size
            sizeText = webRequest.getParameter("size");
            if (sizeText == null) {
                // No size
                size = -1;
                log.trace("No size received");
            } else {
                size = Integer.valueOf(sizeText);
            }

            log.trace("Building page {} with size {}", page, size);
            // Checks size. If it is invalid then the default size is used
            if (size > 0) {
                pagination = new DefaultPagination(page, size);
            } else {
                pagination = new DefaultPagination(page);
            }
        }

        return pagination;
    }

    @Override
    public final boolean supportsParameter(final MethodParameter parameter) {
        return Pagination.class.equals(parameter.getParameterType());
    }

}
