
package com.bernardomg.darksouls.explorer.request.argument;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bernardomg.darksouls.explorer.persistence.model.DefaultSort;
import com.bernardomg.darksouls.explorer.persistence.model.Direction;
import com.bernardomg.darksouls.explorer.persistence.model.DisabledSort;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

public final class SortArgumentResolver
        implements HandlerMethodArgumentResolver {

    public SortArgumentResolver() {
        super();
    }

    @Override
    public final Sort resolveArgument(final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory) throws Exception {
        final String property;
        final String directionText;
        final Direction direction;
        final Sort sort;

        property = webRequest.getParameter("property");
        directionText = webRequest.getParameter("direction");

        if (property == null) {
            sort = new DisabledSort();
        } else {
            if (directionText == null) {
                direction = Direction.ASC;
            } else {
                direction = Direction.valueOf(directionText.toUpperCase());
            }

            sort = new DefaultSort(property, direction);
        }

        return sort;
    }

    @Override
    public final boolean supportsParameter(final MethodParameter parameter) {
        return Pagination.class.equals(parameter.getParameterType());
    }

}
