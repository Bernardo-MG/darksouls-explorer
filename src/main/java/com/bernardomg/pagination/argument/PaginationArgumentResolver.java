/**
 * Copyright 2021-2022 the original author or authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.bernardomg.pagination.argument;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

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

    /**
     * Default constructor.
     */
    public PaginationArgumentResolver() {
        super();
    }

    @Override
    public final Pagination resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        final String     pagedText;
        final String     pageText;
        final String     sizeText;
        final Boolean    paged;
        final Integer    page;
        final Integer    size;
        final Pagination pagination;

        pagedText = webRequest.getParameter("paged");
        paged = parsePaged(pagedText);

        if (paged) {
            pageText = webRequest.getParameter("page");
            sizeText = webRequest.getParameter("size");

            if ((pageText == null) && (sizeText == null)) {
                // No pagination parameters
                pagination = Pagination.first();
                log.trace("No pagination received, using disabled pagination");
            } else {
                page = parsePage(pageText);
                size = parseSize(sizeText);

                log.trace("Building page {} with size {}", page, size);
                // Checks size. If it is invalid then the default size is used
                if (size > 0) {
                    pagination = Pagination.of(page, size);
                } else {
                    log.trace("Invalid size {}, using default size", size);
                    pagination = Pagination.of(page);
                }
            }
        } else {
            pagination = Pagination.disabled();
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
     * @param pageText
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
     * Transforms the paged text into its boolean value.
     *
     * @param pagedText
     *            text with the pagination paged flag
     * @return paged as boolean
     */
    private final Boolean parsePaged(final String pagedText) {
        final Boolean paged;

        if (pagedText == null) {
            paged = true;
        } else {
            paged = Boolean.valueOf(pagedText);
        }

        return paged;
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
