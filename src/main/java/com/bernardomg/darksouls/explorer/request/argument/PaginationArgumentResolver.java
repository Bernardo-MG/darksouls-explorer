
package com.bernardomg.darksouls.explorer.request.argument;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bernardomg.darksouls.explorer.persistence.model.DefaultPagination;
import com.bernardomg.darksouls.explorer.persistence.model.DisabledPagination;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;

public final class PaginationArgumentResolver
        implements HandlerMethodArgumentResolver {

    public PaginationArgumentResolver() {
        super();
    }

    @Override
    public final Pagination resolveArgument(final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory) throws Exception {
        final String pageText;
        final String sizeText;
        final Integer page;
        final Integer size;
        final Pagination pagination;

        pageText = webRequest.getParameter("page");
        sizeText = webRequest.getParameter("size");

        if (pageText == null) {
            pagination = new DisabledPagination();
        } else {
            page = Integer.valueOf(webRequest.getParameter("page"));
            if (sizeText == null) {
                size = 20;
            } else {
                size = Integer.valueOf(sizeText);
            }
            pagination = new DefaultPagination(page, size);
        }

        return pagination;
    }

    @Override
    public final boolean supportsParameter(final MethodParameter parameter) {
        return Pagination.class.equals(parameter.getParameterType());
    }

}
