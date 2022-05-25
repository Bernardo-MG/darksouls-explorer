
package com.bernardomg.darksouls.explorer.request.argument;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bernardomg.darksouls.explorer.persistence.model.DefaultSort;
import com.bernardomg.darksouls.explorer.persistence.model.Direction;
import com.bernardomg.darksouls.explorer.persistence.model.DisabledSort;
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
        final String sortText;
        final String property;
        final String[] sortPieces;
        final Direction direction;
        final Sort sort;

        sortText = webRequest.getParameter("sort");

        if (sortText == null) {
            sort = new DisabledSort();
        } else {
            sortPieces = sortText.split(",");
            property = sortPieces[0];

            if (sortPieces.length == 1) {
                direction = Direction.ASC;
            } else {
                switch (sortPieces[1].toLowerCase()) {
                    case "asc":
                        direction = Direction.ASC;
                        break;
                    case "desc":
                        direction = Direction.DESC;
                        break;
                    default:
                        direction = Direction.ASC;
                }
            }

            sort = new DefaultSort(property, direction);
        }

        return sort;
    }

    @Override
    public final boolean supportsParameter(final MethodParameter parameter) {
        return Sort.class.equals(parameter.getParameterType());
    }

}
