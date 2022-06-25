
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
public final class PaginationArgumentResolver
        implements HandlerMethodArgumentResolver {

    private static final Integer DEFAULT_SIZE = 20;

    public PaginationArgumentResolver() {
        super();
    }

    @Override
    public final Pagination resolveArgument(final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory) throws Exception {
        final String rawPage;
        final String sizeText;
        final Integer page;
        final Integer size;
        final Pagination pagination;

        rawPage = webRequest.getParameter("page");

        if (rawPage == null) {
            // No pagination
            pagination = new DisabledPagination();
            log.trace("No pagination received, using disabled pagination");
        } else {
            page = Integer.valueOf(rawPage);
            sizeText = webRequest.getParameter("size");
            if (sizeText == null) {
                // No size
                size = DEFAULT_SIZE;
                log.trace("No size received, using default page size: {}",
                    size);
            } else {
                size = Integer.valueOf(sizeText);
            }
            log.trace("Building page {} with size {}", page, size);
            pagination = new DefaultPagination(page, size);
        }

        return pagination;
    }

    @Override
    public final boolean supportsParameter(final MethodParameter parameter) {
        return Pagination.class.equals(parameter.getParameterType());
    }

}
