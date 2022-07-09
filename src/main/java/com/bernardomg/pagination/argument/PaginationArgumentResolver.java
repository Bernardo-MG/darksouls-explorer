
package com.bernardomg.pagination.argument;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bernardomg.pagination.model.DefaultPagination;
import com.bernardomg.pagination.model.Pagination;

import lombok.extern.slf4j.Slf4j;

/**
 * Argument resolver for pagination data.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class PaginationArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Pagination DEFAULT_PAGE = new DefaultPagination(0);

    public PaginationArgumentResolver() {
        super();
    }

    /**
     * Default constructor.
     */
    @Override
    public final Pagination resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        final String     pageText;
        final String     sizeText;
        final Integer    page;
        final Integer    size;
        final Pagination pagination;

        pageText = webRequest.getParameter("page");
        sizeText = webRequest.getParameter("size");

        // TODO: Allow disabling pagination by parameter

        if ((pageText == null) && (sizeText == null)) {
            // No pagination parameters
            pagination = DEFAULT_PAGE;
            log.trace("No pagination received, using disabled pagination");
        } else {
            page = parsePage(pageText);
            size = parseSize(sizeText);

            log.trace("Building page {} with size {}", page, size);
            // Checks size. If it is invalid then the default size is used
            if (size > 0) {
                pagination = new DefaultPagination(page, size);
            } else {
                log.trace("Invalid size {}, using default size", size);
                pagination = new DefaultPagination(page);
            }
        }

        return pagination;
    }

    @Override
    public final boolean supportsParameter(final MethodParameter parameter) {
        return Pagination.class.equals(parameter.getParameterType());
    }

    /**
     * Transforms the page text into its numeric value.
     *
     * @param sizeText
     *            text with the pagination page
     * @return page as integer
     */
    private final Integer parsePage(final String pageText) {
        final Integer page;

        if (pageText == null) {
            page = 0;
        } else {
            page = Integer.valueOf(pageText);
        }

        return page;
    }

    /**
     * Transforms the size text into its numeric value.
     *
     * @param sizeText
     *            text with the pagination size
     * @return size as integer
     */
    private final Integer parseSize(final String sizeText) {
        final Integer size;

        if (sizeText == null) {
            // No size
            size = -1;
            log.trace("No size received");
        } else {
            size = Integer.valueOf(sizeText);
        }

        return size;
    }

}
