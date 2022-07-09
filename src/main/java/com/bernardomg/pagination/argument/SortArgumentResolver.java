
package com.bernardomg.pagination.argument;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bernardomg.pagination.model.Direction;
import com.bernardomg.pagination.model.Sort;

import lombok.extern.slf4j.Slf4j;

/**
 * Argument resolver for sorting data.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class SortArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * Default constructor.
     */
    public SortArgumentResolver() {
        super();
    }

    @Override
    public final Sort resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        final String    sortText;
        final String    property;
        final String[]  sortPieces;
        final Direction direction;
        final Sort      sort;
        final String    rawDirection;

        sortText = webRequest.getParameter("sort");

        if (sortText == null) {
            // No sort
            sort = Sort.disabled();
            log.trace("No sort received, using disabled sort");
        } else {
            log.trace("Received sort code: {}", sortText);
            sortPieces = sortText.split(",");

            if (sortPieces.length == 0) {
                // Invalid sort
                sort = Sort.disabled();
                log.warn("Invalid sort command: {}. Disabling sort", sortText);
            } else {
                property = sortPieces[0];

                if (sortPieces.length == 1) {
                    // No direction
                    direction = Direction.ASC;
                    log.trace("No sort direction received, using default direction: {}", direction);
                } else {
                    rawDirection = sortPieces[1].toLowerCase();
                    if ("desc".equals(rawDirection)) {
                        direction = Direction.DESC;
                    } else {
                        direction = Direction.ASC;
                    }
                }
                log.trace("Sorting by property {} and direction {}", property, direction);
                sort = Sort.of(property, direction);
            }
        }

        return sort;
    }

    @Override
    public final boolean supportsParameter(final MethodParameter parameter) {
        return Sort.class.equals(parameter.getParameterType());
    }

}
